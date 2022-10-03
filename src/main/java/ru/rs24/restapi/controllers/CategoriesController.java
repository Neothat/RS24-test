package ru.rs24.restapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.exceptions.ResourceAlreadyExistsException;
import ru.rs24.restapi.exceptions.ResourceNotFoundException;
import ru.rs24.restapi.mappers.CategoryMapper;
import ru.rs24.restapi.mappers.dtos.CategoryDto;
import ru.rs24.restapi.services.CategoriesService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Контроллер для категорий", description = "CRUD операции над категориями")
public class CategoriesController {

    @Setter (onMethod = @__(@Autowired))
    @Getter
    private CategoriesService categoriesService;

    @GetMapping
    @Operation(
            summary = "Запрос на получение всех категорий",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CategoryDto.class))
                    )
            }
    )
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(getCategoriesService().getAllCategories().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение категории по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CategoryDto.class))
                    )
            }
    )
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Parameter(name = "id", description = "id категории", required = true, example = "1")
                                                                   Long id) {
        Category category = getCategoriesService().getCategoryById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found, id: " + id));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Запрос на сохранение новой категории",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = CategoryDto.class))
                    )
            }
    )
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody @Parameter(name = "categoryDto", description = "Описание категории", required = true)
                                                                CategoryDto categoryDto) {
        Category category = getCategoriesService().saveCategory(categoryDto.getName(), categoryDto.getShortDescription())
                .orElseThrow(() -> new ResourceAlreadyExistsException("Category already exist " + categoryDto.getName()));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Запрос на изменение категории",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CategoryDto.class))
                    )
            }
    )
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody @Parameter(name = "categoryDto", description = "Описание категории", required = true)
                                                                  CategoryDto categoryDto) {
        Category category = getCategoriesService().updateCategory(categoryDto).orElseThrow(() -> new ResourceNotFoundException("Category not found, id: " + categoryDto.getId()));
        return new ResponseEntity<>(CategoryMapper.INSTANCE.toDto(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Запрос на удаление категории",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = HttpStatus.class))
                    )
            }
    )
    public void deleteCategoryById(@PathVariable @Parameter(name = "id", description = "id категории", required = true, example = "1")
                                               Long id) {
        getCategoriesService().deleteCategoryById(id);
    }
}
