package com.example.mini_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mini_project.model.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long Id);
}
