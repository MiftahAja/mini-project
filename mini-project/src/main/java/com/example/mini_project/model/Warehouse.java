package com.example.mini_project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Kode gudang tidak boleh kosong")
    private String code;

    @NotBlank(message = "Nama gudang tidak boleh kosong")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseStock> warehouseStock;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouseTo")
    private List<StockMutation> toStockMutation;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouseFrom")
    private List<StockMutation> fromStockMutation;

}
