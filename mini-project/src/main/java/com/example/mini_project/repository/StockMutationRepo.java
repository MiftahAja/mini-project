package com.example.mini_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_project.model.StockMutation;

@Repository
public interface StockMutationRepo extends JpaRepository<StockMutation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE StockMutation s SET s.product = null WHERE s.product.id = :productId")
    void nullifyProduct(Long productId);
}
