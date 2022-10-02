package ru.rs24.restapi.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.mappers.dtos.ProductDto;
import ru.rs24.restapi.repositories.ProductsRepository;
import ru.rs24.restapi.services.CategoriesService;
import ru.rs24.restapi.services.ProductsService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Getter
public class ProductsServiceImpl implements ProductsService {

    @Setter(onMethod = @__(@Autowired))
    private ProductsRepository productsRepository;
    @Setter(onMethod = @__(@Autowired))
    private CategoriesService categoriesService;

    @Override
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productsRepository.findById(id);
    }

    @Override
    public Optional<Product> saveProduct(Product product) {
        String productName = product.getName();
        if (productName != null && !productName.isBlank() && productsRepository.findByName(productName).isEmpty() && product.getCategory() != null) {
            log.info("Created a new product named \"{}\"", productName);
            return Optional.of(productsRepository.save(product));
        }
        log.warn("Saving product named \"{}\" aborted", productName);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(ProductDto productDto) {
        Optional<Product> productOptional = getProductById(productDto.getId());
        Category category = categoriesService.getCategoryByName(productDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found, name: " + productDto.getCategory()));
        String productDtoName = productDto.getName();
        if (productDtoName != null && !productDtoName.isBlank() && productOptional.isPresent() && category != null) {
            Product product = productOptional.get();
            product.setName(productDtoName);
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setImage(productDto.getImage());
            product.setCategory(category);
            product.setStatus(productDto.isStatus());
            log.info("Changes made to the product with id \"{}\"", productDto.getId());
            return Optional.of(product);
        }
        log.warn("Update category with id \"{}\" aborted", productDto.getId());
        return Optional.empty();
    }

    @Override
    public void deleteProductById(Long id) {
        productsRepository.deleteById(id);
    }
}
