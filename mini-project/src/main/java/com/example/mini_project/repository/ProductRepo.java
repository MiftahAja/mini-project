package com.example.mini_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.mini_project.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>   {
    List<Product> findByIsActive(Boolean isActive);

    @Query("SELECT p FROM Product p WHERE p.skuKode = :skuKode ")
    Product findBySkuKode(String skuKode);

    Product findBySkuKodeAndIsActive(String skuKode, Boolean isActive);
}
