package com.backendapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendapi.api.dtos.reqdto.CartItemRequest;
import com.backendapi.api.service.CartService;
import com.backendapi.api.model.CartItemsModel;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/cart")
@RequiredArgsConstructor 
public class CartItemController {

    private final CartService cartService;

    @PostMapping ("/")
    public ResponseEntity<String> addToChart(@RequestHeader("X-USER-ID") String userId,@RequestBody  CartItemRequest cartItemRequest){
        if(!cartService.addToCart(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product Out of the stock or User not found or Product notfound");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @DeleteMapping ("/items/{productId}")
    public ResponseEntity<Boolean> removeFromCart(
      @RequestHeader("X-USER-ID")  String userId,
      @PathVariable  Long productId
    ){
      boolean deleted =  cartService.deleteCartItems(userId,productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    ResponseEntity<List<CartItemsModel>> fetchCartItems(@RequestHeader("X-USER-ID") String userId){
        
         return new ResponseEntity<>(cartService.fetchCartItems(userId),HttpStatus.OK);
    }
}
