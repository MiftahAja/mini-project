package com.example.mini_project.controller;

import com.example.mini_project.service.WarehouseStockService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.mini_project.request.UpdateProduct;
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
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<Product>builder()
                .status("Success")
                .message("Product berhasil ditambahkan")
                .data(productService.tambahProduct(requestCreate))
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product gagal ditambahkan")
                    .build());
        }

    }

    @GetMapping
    public ResponseEntity<WebResponse<List<Product>>> getAllProduct(Product product) {
        try {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<Product>>builder()
                .status("Success")
                .message("Berhasil mengambil semua product")
                .data(productService.getAllProduct())
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<List<Product>>builder()
                    .status("Failed")
                    .message("Product tidak ditemukan")
                    .build());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<WebResponse<List<Product>>> getProductIsActive(Product product) {
        try {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<Product>>builder()
                .status("Success")
                .message("Berhasil mengambil semua product")
                .data(productService.getProductIsActive(product))
                .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<List<Product>>builder()
                    .status("Failed")
                    .message("Product tidak ditemukan")
                    .build());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WebResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody UpdateProduct requestUpdate) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                WebResponse.<Product>builder()
                    .status("Success")
                    .message("Product berhasil diubah")
                    .data(productService.updateProduct(id, requestUpdate))
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product yang ingin diubah tidak ditemukan")
                    .build());
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product gagal diubah")
                    .build());
        }
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<WebResponse<Product>> removeProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                WebResponse.<Product>builder()
                    .status("Success")
                    .message("Product berhasil dihapus")
                    .data(productService.removeProduct(id))
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Tidak ada product yang dapat dihapus")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product gagal dihapus")
                    .build());
        }
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<WebResponse<Product>> restoreProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                WebResponse.<Product>builder()
                    .status("Success")
                    .message("Product berhasil direstore")
                    .data(productService.restoreProduct(id))
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Tidak ada product yang dapat direstore")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product gagal direstore")
                    .build());
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<WebResponse<List<WarehouseStock>>> lowStock() {
        try {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<WarehouseStock>>builder()
                .status("Success")
                .message("Berhasil mengambil semua product low stock")
                .data(warehouseStockService.getLowStock())
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<List<WarehouseStock>>builder()
                    .status("Failed")
                    .message("Product low stock tidak ditemukan")
                    .build());
        }
    }

    @GetMapping("/{sku}/stock-summary")
    public ResponseEntity<WebResponse<Integer>> amountWarehouseStocks(@PathVariable String sku) {

        Integer total = warehouseStockService.amountWarehouseStocks(sku);

        try {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<Integer>builder()
                .status("Success")
                .message("Berhasil mengambil stock product dengan sku " + sku )
                .data(total)
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Integer>builder()
                    .status("Failed")
                    .message("Product dengan sku " + sku + " tidak ditemukan")
                    .build());
        }
    }   

    @DeleteMapping("{id}")
    public ResponseEntity<WebResponse<Product>> deleteProduct(@PathVariable Long id, Product product) {
        productService.deleteProduct(id);

        try {
        return ResponseEntity.status(HttpStatus.OK).body(
                WebResponse.<Product>builder()
                    .status("Success")
                    .message("Product berhasil dihapus")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Product>builder()
                    .status("Failed")
                    .message("Product gagal dihapus")
                    .build());
        }
    }
}