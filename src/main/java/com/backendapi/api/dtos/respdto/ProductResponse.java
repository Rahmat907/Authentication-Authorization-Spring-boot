package com.backendapi.api.dtos.respdto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class ProductResponse {
    private String productId;
    private String title;
    private String description;
    private String category;
    private String brand ;
    private BigDecimal price;
    private BigDecimal salesPrice;
    private Integer stockQuantity;
    private String imageUrl;
}
