package com.example.mini_project.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddWarehouseStock {

    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @JsonProperty("product_id")
    private Long productId;

    private Integer quantity;
}
