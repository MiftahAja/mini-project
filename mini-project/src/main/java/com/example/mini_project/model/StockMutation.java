package com.example.mini_project.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class StockMutation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id")
    private Warehouse warehouseTo;

    @ManyToOne
    @JoinColumn(name = "from_warehouse_id")
    private Warehouse warehouseFrom;

    private String type;

    private Integer quantity;

    private LocalDateTime timestamp;

}
