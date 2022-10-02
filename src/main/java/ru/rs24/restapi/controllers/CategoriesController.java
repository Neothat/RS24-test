package ru.rs24.restapi.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.exceptions.ResourceNotFoundException;
import ru.rs24.restapi.mappers.CategoryMapper;
import ru.rs24.restapi.mappers.dtos.CategoryDto;
import ru.rs24.restapi.services.CategoriesService;
import ru.rs24.restapi.services.ProductsService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {

    @Setter (onMethod = @__(@Autowired))
    @Getter
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(getCategoriesService().getAllCategories().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = getCategoriesService().getCategoryById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found, id: " + id));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = getCategoriesService().saveCategory(categoryDto.getName(), categoryDto.getShortDescription())
                .orElseThrow(() -> new ResourceNotFoundException("Category already exist " + categoryDto.getName()));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = getCategoriesService().updateCategory(categoryDto).orElseThrow(() -> new ResourceNotFoundException("Category not found, id: " + categoryDto.getId()));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryById(@PathVariable Long id) {
        getCategoriesService().deleteCategoryById(id);
    }
}
