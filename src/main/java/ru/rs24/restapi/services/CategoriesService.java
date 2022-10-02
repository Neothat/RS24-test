package ru.rs24.restapi.services;

import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.mappers.dtos.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Optional<Category> getCategoryByName(String name);

    Optional<Category> saveCategory(String name, String shortDescription);

    Optional<Category> updateCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long id);
}
