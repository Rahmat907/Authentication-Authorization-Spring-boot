package com.backendapi.api.dtos.reqdto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ProductRequest {
    private String title;
    private String description;
    private String category;
    private String brand ;
    private BigDecimal price;
    private BigDecimal salesPrice;
    private Integer stockQuantity;
    private String imageUrl;
}
