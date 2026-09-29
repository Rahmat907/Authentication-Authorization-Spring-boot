package com.backendapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendapi.api.dtos.reqdto.ProductRequest;
import com.backendapi.api.dtos.respdto.ProductResponse;
import com.backendapi.api.service.ProductService;

@RestController 
@RequestMapping("/api") 
public class ProductController {
    private final ProductService productService;

    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @PostMapping ("/")
    ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest ){
     return new ResponseEntity<>(productService.createProduct(productRequest),HttpStatus.CREATED);   
    }
}
