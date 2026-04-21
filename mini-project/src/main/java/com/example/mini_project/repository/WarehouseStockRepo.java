package com.example.mini_project.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.mini_project.model.Product;
import com.example.mini_project.model.Warehouse;
import com.example.mini_project.model.WarehouseStock;

@Repository
public interface WarehouseStockRepo extends JpaRepository<WarehouseStock, Long> {
    Optional<WarehouseStock> findByProductAndWarehouse(Product product, Warehouse warehouse);

    @Query(" SELECT ws FROM WarehouseStock ws WHERE ws.quantity < 10 AND ws.product.isActive = true ")
    List<WarehouseStock> findLowStock();

    @Query(" SELECT SUM(ws.quantity) FROM WarehouseStock ws WHERE ws.product.skuKode = :skuKode AND ws.product.isActive = true ")
    Integer amountWarehouseStocks(String skuKode);
}
