package com.example.mini_project.exception;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.mini_project.response.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebResponse<String>> handleRuntimeException(RuntimeException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<List<String>>> handleValidation(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<List<String>>builder()
                .status("Bad Request")
                .message("Input tidak valid")
                .data(errorMessages)
                .build());
    }   

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<WebResponse<String>> handleOptimisticLocking(ObjectOptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Data telah diubah oleh user lain")
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Terjadi kesalahan pada server")
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Data tidak ditemukan")
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<WebResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Input tidak valid")
                .build());
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<WebResponse<String>> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Input tidak valid")
                .build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<WebResponse<String>> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            WebResponse.<String>builder()
                .status("Failed")
                .message("Input tidak valid")
                .build());
    }
}
