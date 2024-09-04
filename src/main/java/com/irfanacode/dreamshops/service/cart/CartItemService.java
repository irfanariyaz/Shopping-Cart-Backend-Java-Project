package com.irfanacode.dreamshops.service.cart;

import com.irfanacode.dreamshops.exceptions.ResourceNotFoundException;
import com.irfanacode.dreamshops.model.Cart;
import com.irfanacode.dreamshops.model.CartItem;
import com.irfanacode.dreamshops.model.Product;
import com.irfanacode.dreamshops.repository.CartItemRepository;
import com.irfanacode.dreamshops.repository.CartRepository;
import com.irfanacode.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final  ICartService cartService;

    @Override
    public void addItemsToCart(Long cartId, Long productId, int quantity) {
        //1.get the cart
        //2.get the products
        //3.check if item already in cart
        //4.if yes, increase the quantity with the requested quantity
        //5.if no, initiate a new cartItem entry

        Cart cart = cartService.getCart(cartId);
        Product  product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if(cartItem.getId() ==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());

        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);


    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        //1.get the cart
        //2.iterate to check if the product exist
        //3.if not throw resourceNotFoundException
        //4.else remove the item from cart
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId,productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                            item.setQuantity(quantity);
                            item.setUnitPrice(item.getProduct().getPrice());
                            item.setTotalPrice();
                        });
         cart.updateTotalAmount();
       // cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
    }
}
