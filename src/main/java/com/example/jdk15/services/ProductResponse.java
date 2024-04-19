package com.example.jdk15.services;

import com.example.jdk15.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductResponse extends JpaRepository<Products, Integer> {
    List<Products> findByNameContainingIgnoreCase(String name);
}
