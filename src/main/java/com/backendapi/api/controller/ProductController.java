package com.backendapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendapi.api.dtos.reqdto.ProductRequest;
import com.backendapi.api.dtos.respdto.ProductResponse;
import com.backendapi.api.service.ProductService;

@RestController 
@RequestMapping("/api/products") 
public class ProductController {
    private final ProductService productService;

    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @PostMapping ("/")
    ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest ){
     return new ResponseEntity<>(productService.createProduct(productRequest),HttpStatus.CREATED);   
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest).map(ResponseEntity :: ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    ResponseEntity<List<ProductResponse>> getAllProduct( ){
     return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);   
    }
}
