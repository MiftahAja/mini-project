package com.example.mini_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mini_project.model.WarehouseStock;
import com.example.mini_project.request.AddWarehouseStock;
import com.example.mini_project.request.TransferStockWarehouse;
import com.example.mini_project.response.WebResponse;
import com.example.mini_project.service.WarehouseStockService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stock")
public class WarehouseStockC {
    private WarehouseStockService warehouseStockService;

    public WarehouseStockC(WarehouseStockService warehouseStockService) {
        this.warehouseStockService = warehouseStockService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<WarehouseStock>> stockIn(@Valid @RequestBody AddWarehouseStock request) {
        warehouseStockService.stockIn(request);
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<WarehouseStock>builder()
                .status("Success")
                .message("Stock berhasil ditambahkan")
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<WarehouseStock>builder()
                    .status("Failed")
                    .message("Stock gagal ditambahkan")
                    .build());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<WebResponse<WarehouseStock>> transferStock(@Valid @RequestBody TransferStockWarehouse request) {
        warehouseStockService.transferStock(request);
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<WarehouseStock>builder()
                .status("Success")
                .message("Stock berhasil ditambahkan")
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<WarehouseStock>builder()
                    .status("Failed")
                    .message("Stock gagal ditambahkan")
                    .build());
        }
    }
}
