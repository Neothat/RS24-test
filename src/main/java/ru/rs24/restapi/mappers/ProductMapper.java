package ru.rs24.restapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.rs24.restapi.entities.Product;
import ru.rs24.restapi.mappers.dtos.ProductDto;

@Mapper(uses = {CategoryNameMapper.class, CategoryObjectMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "dateAdded", source = "dateAdded")
    @Mapping(target = "status", source = "status")
    ProductDto toDto(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "dateAdded", source = "dateAdded")
    @Mapping(target = "status", source = "status")
    Product toObject(ProductDto productDto);
}