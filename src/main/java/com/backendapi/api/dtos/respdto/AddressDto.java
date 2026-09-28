package com.backendapi.api.dtos.respdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class AddressDto {
   
     private String street;
    private String city;
    private String country;
    private Long pincode;
    private Long phoneno ;
    private String notes;
}
