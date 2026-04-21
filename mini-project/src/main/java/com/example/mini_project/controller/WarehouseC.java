package com.example.mini_project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mini_project.model.Warehouse;
import com.example.mini_project.request.AddWarehouse;
import com.example.mini_project.response.WebResponse;
import com.example.mini_project.service.WarehouseService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/warehouse")
public class WarehouseC {
    private WarehouseService warehouseService;

    public WarehouseC(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public WebResponse<Warehouse> tambahGudang(@Valid @RequestBody AddWarehouse warehouseCreate) {
        return WebResponse.<Warehouse>builder()
            .status("Success")
            .message("Gudang berhasil ditambahkan")
            .data(warehouseService.tambahGudang(warehouseCreate))
            .build();
    }

    @GetMapping
    public WebResponse<List<Warehouse>> getAllGudang() {
        return WebResponse.<List<Warehouse>>builder()
            .status("Success")
            .message("Berhasil mengambil semua gudang")
            .data(warehouseService.getAllGudang())
            .build();
    }
}
