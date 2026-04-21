package com.example.mini_project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isActive = true; 

    @NotBlank(message = "Product sku kode must not be empty")
    @Column(unique = true)
    private String skuKode;
    
    @NotBlank(message = "Product name must not be empty")
    private String name;

    @NotNull(message = "Product price must not be empty")
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Version
    private int version;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<WarehouseStock> warehouseStock;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<StockMutation> stockMutation;
}
