package com.example.mini_project.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddWarehouse {
    @NotBlank(message = "Nama gudang tidak boleh kosong")
    private String name;

    @NotBlank(message = "Kode gudang tidak boleh kosong")
    private String code;

}
