package com.example.mini_project.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mini_project.model.StockMutation;
import com.example.mini_project.response.WebResponse;
import com.example.mini_project.service.StockMutationService;

@RestController
@RequestMapping("/api/stock-mutation")
public class StockMutationC {
    private StockMutationService stockMutationService;

    public StockMutationC(StockMutationService stockMutationService) {
        this.stockMutationService = stockMutationService;
    }

    @GetMapping("/top5")
    public ResponseEntity<WebResponse<List<StockMutation>>> getTop5() {
    return ResponseEntity.status(HttpStatus.OK).body(
        WebResponse.<List<StockMutation>>builder()
            .status("Success")
            .message("Berhasil mengambil Top 5 stock mutation")
            .data(stockMutationService.getTop5())
            .build());
    }

    @GetMapping("/top5/in")
    public ResponseEntity<WebResponse<List<StockMutation>>> getTop5In() {
    return ResponseEntity.status(HttpStatus.OK).body(
        WebResponse.<List<StockMutation>>builder()
            .status("Success")
            .message("Berhasil mengambil Top 5 stock mutation")
            .data(stockMutationService.getTop5ByIn())
            .build());
    }

    @GetMapping("/top5/out")
    public ResponseEntity<WebResponse<List<StockMutation>>> getTop5Out() {
    return ResponseEntity.status(HttpStatus.OK).body(
        WebResponse.<List<StockMutation>>builder()
            .status("Success")
            .message("Berhasil mengambil Top 5 stock mutation")
            .data(stockMutationService.getTop5ByOut())
            .build());
    }
}
