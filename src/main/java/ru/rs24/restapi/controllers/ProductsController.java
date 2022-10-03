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
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.exceptions.ResourceAlreadyExistsException;
import ru.rs24.restapi.exceptions.ResourceNotFoundException;
import ru.rs24.restapi.mappers.ProductMapper;
import ru.rs24.restapi.mappers.dtos.CategoryDto;
import ru.rs24.restapi.mappers.dtos.ProductDto;
import ru.rs24.restapi.services.CategoriesService;
import ru.rs24.restapi.services.ProductsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@Getter
@Tag(name = "Контроллер для продуктов", description = "CRUD операции над продуктами")
public class ProductsController {

    @Setter(onMethod = @__(@Autowired))
    private ProductsService productsService;

    @Setter(onMethod = @__(@Autowired))
    private CategoriesService categoriesService;

    @GetMapping
    @Operation(
            summary = "Запрос на получение всех продуктов с фильтрацией",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam (name = "category", required = false)
            @Parameter(name = "category", description = "имя категории", example = "Vegetables")
                    String category,
            @RequestParam(name = "name_part", required = false)
            @Parameter(name = "namePart", description = "часть имени продукта", example = "Pomodo")
                    String namePart,
            @RequestParam(name = "min_price", required = false)
            @Parameter(name = "minPrice", description = "минимальная цена", example = "10")
                    Integer minPrice,
            @RequestParam(name = "max_price", required = false)
            @Parameter(name = "maxPrice", description = "максимальная цена", example = "100")
                    Integer maxPrice
    ) {
        return new ResponseEntity<>(getProductsService().getAllProducts(category, namePart, minPrice, maxPrice).stream()
                .filter(product -> product.getStatus().equals(Boolean.TRUE))
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Parameter(name = "id", description = "id продукта", required = true, example = "1")
                                                                 Long id) {
        Product product = getProductsService().getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Запрос на сохранение нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody @Parameter(name = "categoryDto", description = "Описание продукта", required = true)
                                                              ProductDto productDto) {
        Category category = getCategoriesService().getCategoryByName(productDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found, name: " + productDto.getName()));
        Product draftProduct = ProductMapper.INSTANCE.toObject(productDto);
        draftProduct.setCategory(category);
        Product product = getProductsService().saveProduct(draftProduct)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Product already exist " + productDto.getName()));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Запрос на изменение продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody @Parameter(name = "categoryDto", description = "Описание продукта", required = true)
                                                                ProductDto productDto) {
        Product product = getProductsService().updateProduct(productDto)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Product not found, id: " + productDto.getId()));
        return new ResponseEntity<>(ProductMapper.INSTANCE.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = HttpStatus.class))
                    )
            }
    )
    public void deleteProductById(@PathVariable @Parameter(name = "id", description = "id продукта", required = true, example = "1")
                                              Long id) {
        getProductsService().deleteProductById(id);
    }
}
