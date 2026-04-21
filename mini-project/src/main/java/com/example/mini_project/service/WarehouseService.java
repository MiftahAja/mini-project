package com.example.mini_project.service;

import com.example.mini_project.repository.WarehouseRepo;
import com.example.mini_project.request.AddWarehouse;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.mini_project.model.Warehouse;

@Service
public class WarehouseService {
    private WarehouseRepo warehouseRepo;

    public WarehouseService(WarehouseRepo warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    public Warehouse tambahGudang(AddWarehouse warehouseCreate) {
        Warehouse warehouse = new Warehouse();

        warehouse.setCode(warehouseCreate.getCode());
        if (warehouseRepo.existsByCode(warehouseCreate.getCode())) {
            throw new IllegalArgumentException("Gudang dengan code " + warehouseCreate.getCode() + " sudah ada");
        }

        warehouse.setName(warehouseCreate.getName());
        if (warehouseRepo.existsByName(warehouseCreate.getName())) {
            throw new IllegalArgumentException("Gudang dengan nama " + warehouseCreate.getName() + " sudah ada");
        }


        warehouseRepo.save(warehouse);

        return warehouse;
    }

    public List<Warehouse> getAllGudang() {
        return warehouseRepo.findAll();
    }
}
