package com.example.mini_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_project.model.StockMutation;

@Repository
public interface StockMutationRepo extends JpaRepository<StockMutation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE StockMutation s SET s.product = null WHERE s.product.id = :productId")
    void nullifyProduct(Long productId);

    @Query("SELECT s FROM StockMutation s WHERE s.product IS NOT NULL AND s.product.isActive = true ORDER BY s.timestamp DESC LIMIT 5")
    List<StockMutation> findTop5ByOrderByTimestampDesc();

    @Query("SELECT s FROM StockMutation s WHERE s.product IS NOT NULL AND s.product.isActive = true AND s.type = :type ORDER BY s.timestamp DESC LIMIT 5")
    List<StockMutation> findTop5ByOrderByTimestampDescIn(@Param("type") String type);

    @Query("SELECT s FROM StockMutation s WHERE s.product IS NOT NULL AND s.product.isActive = true AND s.type = :type ORDER BY s.timestamp DESC LIMIT 5")
    List<StockMutation> findTop5ByOrderByTimestampDescOut(@Param("type") String type);
}
