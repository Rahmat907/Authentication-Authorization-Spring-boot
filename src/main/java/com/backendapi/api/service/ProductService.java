package com.backendapi.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backendapi.api.dtos.reqdto.ProductRequest;
import com.backendapi.api.dtos.respdto.ProductResponse;
import com.backendapi.api.model.ProductModel;
import com.backendapi.api.repo.ProductRepo;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductModel productModel = new ProductModel();
        updateProductModel(productModel, productRequest);
        ProductModel productCreated = productRepo.save(productModel);
        return mapToProductResponse(productCreated);
    }

    private ProductResponse mapToProductResponse(ProductModel productCreated) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(String.valueOf(productCreated.getProductId()));
        productResponse.setTitle(productCreated.getTitle());
        productResponse.setBrand(productCreated.getBrand());
        productResponse.setCategory(productCreated.getCategory());
        productResponse.setDescription(productCreated.getDescription());
        productResponse.setImageUrl(productCreated.getImageUrl());
        productResponse.setPrice(productCreated.getPrice());
        productResponse.setSalesPrice(productCreated.getSalesPrice());
        productResponse.setStockQuantity(productCreated.getStockQuantity());
        return productResponse;
    }

    public void updateProductModel(ProductModel productmModel, ProductRequest productRequest) {
        productmModel.setTitle(productRequest.getTitle());
        productmModel.setBrand(productRequest.getBrand());
        productmModel.setCategory(productRequest.getCategory());
        productmModel.setDescription(productRequest.getDescription());
        productmModel.setImageUrl(productRequest.getImageUrl());
        productmModel.setPrice(productRequest.getPrice());
        productmModel.setSalesPrice(productRequest.getSalesPrice());
        productmModel.setStockQuantity(productRequest.getStockQuantity());
    }

    public Optional<ProductResponse> updateProduct(Long productId,ProductRequest productRequest) {
          return  productRepo.findById(productId)
            .map(exitingProduct ->{
               updateProductModel(exitingProduct,productRequest);
               ProductModel savedProduct = productRepo.save(exitingProduct);
                return mapToProductResponse(savedProduct);
            });
        
    }
    public List<ProductResponse> getAllProduct(){
        return productRepo.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList()) ;
    }
}
