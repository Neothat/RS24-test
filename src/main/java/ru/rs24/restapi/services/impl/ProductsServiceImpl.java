package ru.rs24.restapi.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.exceptions.ResourceNotFoundException;
import ru.rs24.restapi.mappers.dtos.ProductDto;
import ru.rs24.restapi.repositories.ProductsRepository;
import ru.rs24.restapi.repositories.specifications.ProductsSpecifications;
import ru.rs24.restapi.services.CategoriesService;
import ru.rs24.restapi.services.ProductsService;

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
    public List<Product> getAllProducts(String category, String namePart, Integer minPrice, Integer maxPrice) {
        Specification<Product> spec = Specification.where(null);

        if (category != null) {
            Optional<Category> categoryOptional = getCategoriesService().getCategoryByName(category);
            if (categoryOptional.isPresent()) {
                spec = spec.and(ProductsSpecifications.categoryEquals(categoryOptional.get()));
            }
        }
        if (namePart != null) {
            spec = spec.and(ProductsSpecifications.nameLike(namePart));
        }
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        return getProductsRepository().findAll(spec);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return getProductsRepository().findById(id);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return getProductsRepository().findByCategory(category);
    }

    @Override
    public Optional<Product> saveProduct(Product product) {
        String productName = product.getName();
        if (productName != null && !productName.isBlank() && getProductsRepository().findByName(productName).isEmpty() && product.getCategory() != null) {
            product.setDateAdded(System.currentTimeMillis());
            log.info("Created a new product named \"{}\"", productName);
            return Optional.of(getProductsRepository().save(product));
        }
        log.warn("Saving product named \"{}\" aborted", productName);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(ProductDto productDto) {
        Optional<Product> productOptional = getProductById(productDto.getId());
        Category category = getCategoriesService().getCategoryByName(productDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found, name: " + productDto.getCategory()));
        String productDtoName = productDto.getName();
        if (productDtoName != null && !productDtoName.isBlank() && productOptional.isPresent() && category != null) {
            Product product = productOptional.get();
            product.setName(productDtoName);
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setImage(productDto.getImage());
            product.setCategory(category);
            product.setStatus(productDto.isStatus());
            getProductsRepository().save(product);
            log.info("Changes made to the product with id \"{}\"", productDto.getId());
            return Optional.of(product);
        }
        log.warn("Update category with id \"{}\" aborted", productDto.getId());
        return Optional.empty();
    }

    @Override
    public void deleteProductById(Long id) {
        getProductsRepository().deleteById(id);
    }
}
