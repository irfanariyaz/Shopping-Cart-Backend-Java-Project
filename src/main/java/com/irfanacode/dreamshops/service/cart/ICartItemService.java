package com.irfanacode.dreamshops.service.cart;

import com.irfanacode.dreamshops.model.CartItem;

public interface ICartItemService {
    void  addItemsToCart(Long cartId,Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateCartItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
