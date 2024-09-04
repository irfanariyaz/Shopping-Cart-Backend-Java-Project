package com.irfanacode.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items; // Assuming CartItemDto is a DTO for cart items

}
