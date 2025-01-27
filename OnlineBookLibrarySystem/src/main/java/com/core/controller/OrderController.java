package com.core.controller;

import com.core.dto.BorrowRequestDto;
import com.core.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final LibraryService libraryService;

    public OrderController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public String borrowItem(@RequestBody BorrowRequestDto request) {
        return libraryService.borrowItem(request);
    }
}