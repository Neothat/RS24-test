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
    public Optional<Category> getCategoryProductBy(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveProduct(String name, String description, Integer price, String image, Category category, Boolean status) {
        if (productsRepository.findByName(name).isEmpty() && category != null) {
            Product product = new Product(name, description, price, image, category, System.currentTimeMillis(), status);
            productsRepository.save(product);
            log.info("Created a new product named \"{}\"", name);
        }
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(ProductDto productDto) {
        Optional<Product> productOptional = getProductById(productDto.getId());
        Category category = categoriesService.getCategoryByName(productDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found, name: " + productDto.getCategory()));
        if (productOptional.isPresent() && category != null) {
            Product product = productOptional.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setImage(productDto.getImage());
            product.setCategory(category);
            product.setStatus(productDto.isStatus());
            log.info("Changes made to the product with id \"{}\"", productDto.getId());
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public void deleteProductById(Long id) {
        productsRepository.deleteById(id);
    }

    @Override
    public Optional<Product> saveProduct(Product product) {
        if (productsRepository.findByName(product.getName()).isEmpty() && product.getCategory() != null) {
            return Optional.of(productsRepository.save(product));
        }
        return Optional.empty();
    }
}
