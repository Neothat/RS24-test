package ru.rs24.restapi.services;

import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.mappers.dtos.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Optional<Product> saveProduct(Product product);

    Optional<Product> updateProduct(ProductDto productDto);

    void deleteProductById(Long id);
}
