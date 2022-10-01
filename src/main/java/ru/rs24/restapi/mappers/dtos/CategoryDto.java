package ru.rs24.restapi.mappers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id;
    private String name;
    private String shortDescription;

    public CategoryDto(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

}