package com.irfanacode.dreamshops.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Order order,  Product product, int quantity, BigDecimal price) {
        this.order = order;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }


    // Other fields and methods
}
