package com.example.mini_project.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.mini_project.model.StockMutation;
import com.example.mini_project.repository.StockMutationRepo;

@Service
public class StockMutationService {
    private StockMutationRepo stockMutationRepo;

    public StockMutationService(StockMutationRepo stockMutationRepo) {
        this.stockMutationRepo = stockMutationRepo;
    }

    public List<StockMutation> getTop5() {
        List<StockMutation> stockMutations = stockMutationRepo.findTop5ByOrderByTimestampDesc();

        Collections.reverse(stockMutations);
        return stockMutations;
    }

    public List<StockMutation> getTop5ByIn() {
        List<StockMutation> stockMutations = stockMutationRepo.findTop5ByOrderByTimestampDescIn("IN");

        Collections.reverse(stockMutations);
        return stockMutations;
    }

    public List<StockMutation> getTop5ByOut() {
        List<StockMutation> stockMutations = stockMutationRepo.findTop5ByOrderByTimestampDescOut("OUT");

        Collections.reverse(stockMutations);
        return stockMutations;
    }
}
