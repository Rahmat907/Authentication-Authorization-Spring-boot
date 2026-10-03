package com.backendapi.api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity 
@AllArgsConstructor 
@Data 
@NoArgsConstructor 
@Table (name =  "orderItems")
public class OrderItems {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne 
    @JoinColumn (name= "product_id", nullable = false)
    private ProductModel product;
    private Integer quantity;
    private BigDecimal price;

    @ManyToOne 
    @JoinColumn (name = "order_id", nullable = false)
    private OrderModel order;
}
