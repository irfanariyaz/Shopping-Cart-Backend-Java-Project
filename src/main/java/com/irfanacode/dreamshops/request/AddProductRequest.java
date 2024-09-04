package com.irfanacode.dreamshops.request;

import com.irfanacode.dreamshops.model.Category;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private  Long id;
    private  String name;
    private String brand;
    private  String description;
    private BigDecimal price;
    private  int inventory;    //quantity.to tract number of product
    private Category category;
}
