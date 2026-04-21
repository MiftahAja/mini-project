package com.example.mini_project.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import com.example.mini_project.model.Category;
import com.example.mini_project.response.WebResponse;
import com.example.mini_project.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryC {
    private CategoryService categoryService;

    public CategoryC(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    public ResponseEntity<WebResponse<Category>> tambahCategory(@Valid @RequestBody Category category) {
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<Category>builder()
                .status("Success")
                .message("Category berhasil ditambahkan")
                .data(categoryService.tambahCategory(category))
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Category>builder()
                    .status("Failed")
                    .message("Category gagal ditambahkan")
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            WebResponse.<Category>builder()
                .status("Success")
                .message("Category berhasil diubah")
                .data(categoryService.updateCategory(id, category))
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Category>builder()
                    .status("Failed")
                    .message("Category gagal diubah")
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<Category>>> getAllCategory() {
        try {
        return ResponseEntity.status(HttpStatus.OK).body(
            WebResponse.<List<Category>>builder()
                .status("Success")
                .message("Berhasil mengambil semua category")
                .data(categoryService.getAllCategory())
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<List<Category>>builder()
                    .status("Failed")
                    .message("Category tidak ditemukan")
                    .build());
        }
    }
}
