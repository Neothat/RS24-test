package ru.rs24.restapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> saveProduct() { //@RequestBody ProductDto productDto
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateProduct() { //@RequestBody ProductDto productDto
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        return null;
    }
}
