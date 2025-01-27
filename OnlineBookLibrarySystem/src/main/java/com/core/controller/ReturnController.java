package com.core.controller;

import com.core.dto.ReturnRequestDto;
import com.core.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/return")
public class ReturnController {
    private final LibraryService libraryService;

    public ReturnController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public String returnItems(@RequestBody ReturnRequestDto request) {
        return libraryService.returnItems(request);
    }
}
