package com.irfanacode.dreamshops.service.product;

import com.irfanacode.dreamshops.dto.ImageDto;
import com.irfanacode.dreamshops.dto.ProductDto;
import com.irfanacode.dreamshops.exceptions.AlreadyExistException;
import com.irfanacode.dreamshops.exceptions.ProductNotFoundException;
import com.irfanacode.dreamshops.exceptions.ResourceNotFoundException;
import com.irfanacode.dreamshops.model.Category;
import com.irfanacode.dreamshops.model.Image;
import com.irfanacode.dreamshops.model.Product;
import com.irfanacode.dreamshops.repository.CategoryRepository;
import com.irfanacode.dreamshops.repository.ImageRepository;
import com.irfanacode.dreamshops.repository.ProductRepository;
import com.irfanacode.dreamshops.request.AddProductRequest;
import com.irfanacode.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if category is found in the db
        //if yes ,set it as the new product category
        //if not, create a new category and
        // set it as the new product category
        //create category
        if(product_exist(request.getName(), request.getBrand())){
            throw  new AlreadyExistException(request.getBrand() + " " + request.getName() + " already exists,you may update the product instead!");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    private Boolean product_exist(String name,String brand){
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ResourceNotFoundException("Product not found");});

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct ->updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() ->new ProductNotFoundException("Product not found with id: " + productId));

    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //get products by category name
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }
    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;

    }
}
