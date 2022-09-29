package ru.rs24.restapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> saveCategory() { //@RequestBody ProductDto productDto
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateCategory() { //@RequestBody ProductDto productDto
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        return null;
    }
}
