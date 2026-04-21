package com.example.mini_project.exception;

import java.util.List;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.mini_project.response.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public WebResponse<String> handleRuntimeException(RuntimeException e) {

        return WebResponse.<String>builder()
            .status("Failed")
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResponse<List<String>> handleValidation(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .toList();

        return WebResponse.<List<String>>builder()
            .status("Bad Request")
            .message("Input tidak valid")
            .data(errorMessages)
            .build();
    }   

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public WebResponse<String> handleOptimisticLocking(ObjectOptimisticLockingFailureException e) {
        return WebResponse.<String>builder()
            .status("Failed")
            .message(e.getMessage())
            .build();
    }
}
