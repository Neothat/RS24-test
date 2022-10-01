package ru.rs24.restapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoriesRepository.findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoriesRepository.findByName(name);
    }

    @Override
    public Optional<Category> saveCategory(String name, String shortDescription) {
        if (categoriesRepository.findByName(name).isEmpty()) {
            Category newCategory = new Category(name, shortDescription);
            newCategory = categoriesRepository.save(newCategory);
            log.info("Created a new category named \"{}\"", name);
            return Optional.of(newCategory);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Category> updateCategory(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = getCategoryById(categoryDto.getId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDto.getName());
            category.setShortDescription(categoryDto.getShortDescription());
            categoriesRepository.save(category);
            log.info("Changes made to the category with id \"{}\"", category.getId());
            return Optional.of(category);
        }
        return Optional.empty();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoriesRepository.deleteById(id);
    }
}
