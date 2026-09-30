package com.backendapi.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backendapi.api.model.ProductModel;

@Repository 
public interface ProductRepo extends JpaRepository<ProductModel,Long> {
    @Query ("SELECT p FROM ProductModel AS p WHERE p.stockQuantity > 0 AND LOWER(p.title) LIKE  LOWER(CONCAT('%', :keyword, '%'))" )
    List<ProductModel> searchProducts(@Param("keyword") String keyword);

}
