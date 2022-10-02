package ru.rs24.restapi.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.mappers.dtos.CategoryDto;
import ru.rs24.restapi.repositories.CategoriesRepository;
import ru.rs24.restapi.services.CategoriesService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Getter
public class CategoriesServiceImpl implements CategoriesService {

    @Setter(onMethod = @__(@Autowired))
    private CategoriesRepository categoriesRepository;
    @Setter(onMethod = @__(@Autowired))
    private ProductsServiceImpl productsService;

    @Override
    public List<Category> getAllCategories() {
        return getCategoriesRepository().findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return getCategoriesRepository().findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return getCategoriesRepository().findByName(name);
    }

    @Override
    public Optional<Category> saveCategory(String name, String shortDescription) {
        if (name != null && !name.isBlank() && getCategoriesRepository().findByName(name).isEmpty()) {
            Category newCategory = new Category(name, shortDescription);
            newCategory = getCategoriesRepository().save(newCategory);
            log.info("Created and saved a new category named \"{}\"", name);
            return Optional.of(newCategory);
        }
        log.warn("Saving category named \"{}\" aborted", name);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Category> updateCategory(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = getCategoryById(categoryDto.getId());
        String categoryDtoName = categoryDto.getName();
        if (categoryDtoName != null && !categoryDtoName.isBlank() && categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDtoName);
            category.setShortDescription(categoryDto.getShortDescription());
            getCategoriesRepository().save(category);
            log.info("Changes made to the category with id \"{}\"", category.getId());
            return Optional.of(category);
        }
        log.warn("Update category with id \"{}\" aborted", categoryDto.getId());
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Optional<Category> categoryOptional = getCategoryById(id);
        if (categoryOptional.isPresent()) {
            unlinkCategoryFromProducts(categoryOptional.get());
            getCategoriesRepository().deleteById(id);
            log.info("Category with id: {} removed", id);
        }
    }

    private void unlinkCategoryFromProducts(Category category) {
        getProductsService().getProductsByCategory(category).forEach(product -> {
            product.setCategory(null);
            product.setStatus(Boolean.FALSE);
            log.info("Product \"{}\" without category, product is no longer active", product.getName());
        });
    }
}
