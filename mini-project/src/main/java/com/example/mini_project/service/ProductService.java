package com.example.mini_project.service;

import com.example.mini_project.model.Category;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.mini_project.repository.ProductRepo;
import com.example.mini_project.request.ProductCreate;
import com.example.mini_project.model.Product;
import com.example.mini_project.repository.CategoryRepo;

@Service
public class ProductService {
    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public Product tambahProduct(ProductCreate requestCreate) {
        Category category = categoryRepo.findById(requestCreate.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + requestCreate.getCategoryId()));

        if (requestCreate.getSkuKode() != null) {
            Product product = productRepo.findBySkuKode(requestCreate.getSkuKode());
            if (product != null) {
                throw new RuntimeException("Product dengan sku " + requestCreate.getSkuKode() + " sudah digunakan.");
            }
        }

        Product product = new Product();

        product.setName(requestCreate.getName());
        product.setPrice(requestCreate.getPrice());
        product.setIsActive(true);
        product.setSkuKode(requestCreate.getSkuKode());
        product.setCategory(category);

        productRepo.save(product);

        return product;
    }

    public List<Product> getProductIsActive(Product product) {
        return productRepo.findByIsActive(true);
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Product updateProduct(Long id, Product databaru) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(databaru.getName());
        product.setPrice(databaru.getPrice());
        product.setCategory(databaru.getCategory());

        return productRepo.save(product);
    }

    public Product restoreProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setIsActive(true);
        return productRepo.save(product);
    }

    public Product removeProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setIsActive(false);
        return productRepo.save(product);
    }
}
