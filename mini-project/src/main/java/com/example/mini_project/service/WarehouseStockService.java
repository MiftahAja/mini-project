package com.example.mini_project.service;

import org.springframework.stereotype.Service;
import com.example.mini_project.model.Product;
import com.example.mini_project.model.Warehouse;
import com.example.mini_project.model.WarehouseStock;
import com.example.mini_project.repository.WarehouseStockRepo;
import com.example.mini_project.request.AddWarehouseStock;
import com.example.mini_project.request.TransferStockWarehouse;
import jakarta.transaction.Transactional;
import com.example.mini_project.repository.ProductRepo;
import com.example.mini_project.repository.WarehouseRepo;
import com.example.mini_project.model.StockMutation;
import com.example.mini_project.repository.StockMutationRepo;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class WarehouseStockService {

    private WarehouseStockRepo warehouseStockRepo;
    private ProductRepo productRepo;
    private WarehouseRepo warehouseRepo;
    private StockMutationRepo stockMutationRepo;

    public WarehouseStockService(
        WarehouseStockRepo warehouseStockRepo,
        ProductRepo productRepo,
        WarehouseRepo warehouseRepo,
        StockMutationRepo stockMutationRepo
    ) {
        this.warehouseStockRepo = warehouseStockRepo;
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
        this.stockMutationRepo = stockMutationRepo;
    }

    @Transactional
    public void stockIn(AddWarehouseStock request) {

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }

        Product product = productRepo.findById(request.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Product tidak ditemukan"));

        if(!product.getIsActive()) {
            throw new IllegalArgumentException("Product tidak aktif, tidak dapat ditambahkan ke gudang");
        }

        Warehouse warehouse = warehouseRepo.findById(request.getWarehouseId())
            .orElseThrow(() -> new IllegalArgumentException("Warehouse tidak ditemukan"));

        WarehouseStock stock = warehouseStockRepo
            .findByProductAndWarehouse(product, warehouse)
            .orElse(
                null
            );

        if (stock == null) {
            stock = new WarehouseStock();
            stock.setProduct(product);
            stock.setWarehouse(warehouse);
            stock.setQuantity(request.getQuantity());
        } else {
            stock.setQuantity(stock.getQuantity() + request.getQuantity());
        }

        warehouseStockRepo.save(stock);

        StockMutation mutation = new StockMutation();
        mutation.setProduct(product);
        mutation.setWarehouseTo(warehouse);
        mutation.setQuantity(request.getQuantity());
        mutation.setType("IN");
        mutation.setTimestamp(LocalDateTime.now());

        stockMutationRepo.save(mutation);
    }

    @Transactional
    public void transferStock(TransferStockWarehouse request) {

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }
        
        // if (request.getQuantity()  )

        if (request.getFromWarehouseId().equals(request.getToWarehouseId())) {
            throw new IllegalArgumentException("Warehouse asal dan tujuan tidak boleh sama");
        }

        Product product = productRepo.findById(request.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Product tidak ditemukan"));

        if (!product.getIsActive()) {
            throw new IllegalArgumentException("Product tidak aktif");
        }

        Warehouse fromWarehouse = warehouseRepo.findById(request.getFromWarehouseId())
            .orElseThrow(() -> new IllegalArgumentException("Warehouse asal tidak ditemukan"));

        Warehouse toWarehouse = warehouseRepo.findById(request.getToWarehouseId())
            .orElseThrow(() -> new IllegalArgumentException("Warehouse tujuan tidak ditemukan"));

        // ambil stok asal
        WarehouseStock fromStock = warehouseStockRepo
            .findByProductAndWarehouse(product, fromWarehouse)
            .orElseThrow(() -> new IllegalArgumentException("Stock di warehouse asal tidak ada"));

        if (fromStock.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Stock tidak cukup");
        }

        // ambil stok tujuan
        WarehouseStock toStock = warehouseStockRepo
            .findByProductAndWarehouse(product, toWarehouse)
            .orElse(null);

        // KURANGI dari asal
        fromStock.setQuantity(fromStock.getQuantity() - request.getQuantity());
        warehouseStockRepo.save(fromStock);

        // TAMBAH ke tujuan
        if (toStock == null) {
            toStock = new WarehouseStock();
            toStock.setProduct(product);
            toStock.setWarehouse(toWarehouse);
            toStock.setQuantity(request.getQuantity());
        } else {
            toStock.setQuantity(toStock.getQuantity() + request.getQuantity());
        }

        warehouseStockRepo.save(toStock);

        // CATAT MUTATION (OUT)
        StockMutation outMutation = new StockMutation();
        outMutation.setProduct(product);
        outMutation.setWarehouseTo(toWarehouse);
        outMutation.setQuantity(request.getQuantity());
        outMutation.setType("OUT");
        outMutation.setTimestamp(LocalDateTime.now());

        stockMutationRepo.save(outMutation);

        // CATAT MUTATION (TRANSFER)
        StockMutation inMutation = new StockMutation();
        inMutation.setProduct(product);
        inMutation.setWarehouseFrom(fromWarehouse);
        inMutation.setQuantity(request.getQuantity());
        inMutation.setType("IN");
        inMutation.setTimestamp(LocalDateTime.now());

        stockMutationRepo.save(inMutation);
    }

    public List<WarehouseStock> getLowStock() {
        return warehouseStockRepo.findLowStock();
    }

    public Integer amountWarehouseStocks(String skuKode) {
        Integer total = warehouseStockRepo.amountWarehouseStocks(skuKode);
        
        if (productRepo.findBySkuKodeAndIsActive(skuKode, true) == null) {
            throw new IllegalArgumentException("Product dengan sku " + skuKode + " tidak ditemukan");
        }

        return total != null ? total : 0;
    }
}