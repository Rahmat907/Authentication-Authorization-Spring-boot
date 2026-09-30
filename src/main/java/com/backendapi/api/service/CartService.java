package com.backendapi.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backendapi.api.dtos.reqdto.CartItemRequest;
import com.backendapi.api.model.CartItemsModel;
import com.backendapi.api.model.ProductModel;
import com.backendapi.api.model.UserModel;
import com.backendapi.api.repo.CartItemsRepo;
import com.backendapi.api.repo.ProductRepo;
import com.backendapi.api.repo.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    public final CartItemsRepo cartItemsRepo;
    public final ProductRepo productRepo;
    public final UserRepo userRepo;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        // look for product
        Optional<ProductModel> productOpt = productRepo.findById(cartItemRequest.getProductid());
        if (productOpt.isEmpty())
            return false;

        ProductModel productModel = productOpt.get();

        if (productModel.getStockQuantity() < cartItemRequest.getQuantity())
            return false;

        Optional<UserModel> userOptional = userRepo.findById(Long.parseLong(userId));

        if (userOptional.isEmpty())
            return false;

        UserModel user = userOptional.get();

        CartItemsModel existingcart = cartItemsRepo.findbyUserAndProduct(user,productModel);
        if(existingcart != null){
            // update the qunatity
            existingcart.setQuantity(existingcart.getQuantity() + cartItemRequest.getQuantity());
        }else{
            // create new cart
            CartItemsModel newCart = new CartItemsModel();
            newCart.setProductModel(productModel);
            newCart.setUserModel(user);
            newCart.setQuantity(cartItemRequest.getQuantity());

            cartItemsRepo.save(newCart);
        }
        return true;
    }
}
