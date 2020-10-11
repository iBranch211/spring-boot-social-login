package com.spring.social.repository;


import com.spring.social.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
    Boolean existsByName(String name);
}