package com.example.mini_project.controller;

import com.example.mini_project.service.WarehouseStockService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mini_project.model.Product;
import com.example.mini_project.model.WarehouseStock;
import com.example.mini_project.request.ProductCreate;
import com.example.mini_project.response.WebResponse;
import com.example.mini_project.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductC {
    private WarehouseStockService warehouseStockService;
    private ProductService productService;

    public ProductC(ProductService productService, WarehouseStockService warehouseStockService) {
        this.productService = productService;
        this.warehouseStockService = warehouseStockService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<Product>> tambahProduct(@Valid @RequestBody ProductCreate requestCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<Product>builder()
                .status("Success")
                .message("Product berhasil ditambahkan")
                .data(productService.tambahProduct(requestCreate))
                .build());
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<Product>>> getAllProduct(Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<Product>>builder()
                .status("Success")
                .message("Berhasil mengambil semua product")
                .data(productService.getAllProduct())
                .build());
    }

    @GetMapping("/active")
    public ResponseEntity<WebResponse<List<Product>>> getProductIsActive(Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<Product>>builder()
                .status("Success")
                .message("Berhasil mengambil semua product")
                .data(productService.getProductIsActive(product))
                .build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WebResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                WebResponse.<Product>builder()
                    .status("Success")
                    .message("Product berhasil diubah")
                    .data(productService.updateProduct(id, product))
                    .build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/remove/{id}")
    public WebResponse<Product> removeProduct(@PathVariable Long id) {
        try {
            return WebResponse.<Product>builder()
                .status("Success")
                .message("Product berhasil dihapus")
                .data(productService.removeProduct(id))
                .build();
        } catch (Exception e) {
            return WebResponse.<Product>builder()
                .status("Failed")
                .message(e.getMessage())
                .build();
        }
    }

    @PutMapping("/restore/{id}")
    public WebResponse<Product> restoreProduct(@PathVariable Long id) {
        try {
            return WebResponse.<Product>builder()
                .status("Success")
                .message("Product berhasil dihapus")
                .data(productService.restoreProduct(id))
                .build();
        } catch (Exception e) {
            return WebResponse.<Product>builder()
                .status("Failed")
                .message(e.getMessage())
                .build();
        }
    }

    @GetMapping("/low-stock")
    public WebResponse<List<WarehouseStock>> lowStock() {
        return WebResponse.<List<WarehouseStock>>builder()
            .status("Success")
            .message("Berhasil mengambil semua product low stock")
            .data(warehouseStockService.getLowStock())
            .build();
    }

    @GetMapping("/{sku}/stock-summary")
    public WebResponse<Integer> amountWarehouseStocks(@PathVariable String sku) {

        Integer total = warehouseStockService.amountWarehouseStocks(sku);

        return WebResponse.<Integer>builder()
            .status("Success")
            .message("Berhasil mengambil stock product dengan sku " + sku )
            .data(total)
            .build();
    }   
}