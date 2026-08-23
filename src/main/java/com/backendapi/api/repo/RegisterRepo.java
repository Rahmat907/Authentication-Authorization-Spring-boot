package com.backendapi.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendapi.api.model.UserModel;

@Repository
public interface RegisterRepo extends JpaRepository<UserModel,Long> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
}
