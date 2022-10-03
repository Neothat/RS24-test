package ru.rs24.restapi.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.exceptions.ResourceAlreadyExistsException;
import ru.rs24.restapi.exceptions.ResourceNotFoundException;
import ru.rs24.restapi.mappers.ProductMapper;
import ru.rs24.restapi.mappers.dtos.ProductDto;
import ru.rs24.restapi.services.CategoriesService;
import ru.rs24.restapi.services.ProductsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@Getter
public class ProductsController {

    @Setter(onMethod = @__(@Autowired))
    private ProductsService productsService;

    @Setter(onMethod = @__(@Autowired))
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "name_part", required = false) String namePart,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice
    ) {
        return new ResponseEntity<>(getProductsService().getAllProducts(category, namePart, minPrice, maxPrice).stream()
                .filter(product -> product.getStatus().equals(Boolean.TRUE))
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = getProductsService().getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductDto productDto) {
        Category category = getCategoriesService().getCategoryByName(productDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found, name: " + productDto.getName()));
        Product draftProduct = ProductMapper.INSTANCE.toObject(productDto);
        draftProduct.setCategory(category);
        Product product = getProductsService().saveProduct(draftProduct)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Product already exist " + productDto.getName()));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = getProductsService().updateProduct(productDto)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Product not found, id: " + productDto.getId()));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        getProductsService().deleteProductById(id);
    }
}
