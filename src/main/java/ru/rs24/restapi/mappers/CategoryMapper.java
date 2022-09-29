package ru.rs24.restapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.mappers.dtos.CategoryDto;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source ="name")
    @Mapping(target = "shortDescription", source = "shortDescription")
    CategoryDto toDto(Category category);
}
