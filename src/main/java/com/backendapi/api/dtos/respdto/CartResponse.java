package com.backendapi.api.dtos.respdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CartResponse {
    private  String cartId;
    private UserResponse userResponse;
    private ProductResponse productResponse;
    private Integer quantity;
}
