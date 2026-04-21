package com.example.mini_project.controller;

import com.example.mini_project.service.WarehouseStockService;
import java.util.List;
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
    public WebResponse<Product> tambahProduct(@Valid @RequestBody ProductCreate requestCreate) {
        return WebResponse.<Product>builder()
            .status("Success")
            .message("Product berhasil ditambahkan")
            .data(productService.tambahProduct(requestCreate))
            .build();
    }

    @GetMapping
    public WebResponse<List<Product>> getAllProduct(Product product) {
        return WebResponse.<List<Product>>builder()
            .status("Success")
            .message("Berhasil mengambil semua product")
            .data(productService.getAllProduct())
            .build();
    }

    @GetMapping("/active")
    public WebResponse<List<Product>> getProductIsActive(Product product) {
        return WebResponse.<List<Product>>builder()
            .status("Success")
            .message("Berhasil mengambil semua product")
            .data(productService.getProductIsActive(product))
            .build();
    }

    @PutMapping("/update/{id}")
    public WebResponse<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return WebResponse.<Product>builder()
            .status("Success")
            .message("Product berhasil diubah")
            .data(productService.updateProduct(id, product))
            .build();
    }

    @PutMapping("/remove/{id}")
    public WebResponse<Product> removeProduct(@PathVariable Long id) {
        return WebResponse.<Product>builder()
            .status("Success")
            .message("Product berhasil di false")
            .data(productService.removeProduct(id))
            .build();
    }

    @PutMapping("/restore/{id}")
    public WebResponse<Product> restoreProduct(@PathVariable Long id) {
        return WebResponse.<Product>builder()
            .status("Success")
            .message("Product berhasil di true")
            .data(productService.restoreProduct(id))
            .build();
    }

    @GetMapping("/low-stock")
    public WebResponse<List<WarehouseStock>> lowStock() {
        return WebResponse.<List<WarehouseStock>>builder()
            .status("Success")
            .message("Berhasil mengambil semua product")
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
