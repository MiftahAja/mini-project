package com.example.mini_project.controller;

import java.util.List;
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
    public WebResponse<Category> tambahCategory(@Valid @RequestBody Category category) {
        return WebResponse.<Category>builder()
            .status("Success")
            .message("Category berhasil ditambahkan")
            .data(categoryService.tambahCategory(category))
            .build();
    }

    @PutMapping("/{id}")
    public WebResponse<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return WebResponse.<Category>builder()
            .status("Success")
            .message("Category berhasil diubah")
            .data(categoryService.updateCategory(id, category))
            .build();
    }

    @GetMapping
    public WebResponse<List<Category>> getAllCategory() {
        return WebResponse.<List<Category>>builder()
            .status("Success")
            .message("Berhasil mengambil semua category")
            .data(categoryService.getAllCategory())
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
