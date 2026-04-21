package com.example.mini_project.request;


import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductCreate {

    @NotBlank(message = "Product name must not be empty")
    private String name;

    @NotNull(message = "Product price must not be empty")
    @Min(0)
    private double price;

    @JsonProperty("sku_kode")
    private String skuKode;

    @NotNull(message = "Product quantity must not be empty")
    private Integer quantity;

    @NotNull(message = "Category id must not be empty")
    @JsonProperty("category_id")
    private Long categoryId;


}
