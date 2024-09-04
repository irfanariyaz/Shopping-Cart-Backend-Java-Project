package com.irfanacode.dreamshops.dto;

import com.irfanacode.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private  Long id;
    private  String name;
    private String brand;
    private  String description;
    private BigDecimal price;
    private  int inventory;    //quantity.to tract number of product
    private Category category;
   private List<ImageDto> images;
}
