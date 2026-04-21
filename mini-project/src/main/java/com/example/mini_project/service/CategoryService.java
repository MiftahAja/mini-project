package com.example.mini_project.service;

import com.example.mini_project.model.Category;
import com.example.mini_project.repository.CategoryRepo;
import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Service;
   
@Service
public class CategoryService {
    private CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    public Category tambahCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Category updateCategory(Long id, Category databaru) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(databaru.getName());;
        return categoryRepo.save(category);
    }
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepo.findById(id);
    }
}