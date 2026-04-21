package com.example.mini_project.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferStockWarehouse {

    @JsonProperty("from_warehouse_id")
    private Long fromWarehouseId;

    @JsonProperty("to_warehouse_id")
    private Long toWarehouseId;
    
    @JsonProperty("product_id")
    private Long productId;

    private Integer quantity;
}
