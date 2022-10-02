package ru.rs24.restapi.mappers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    @NotEmpty
    private String name;
    private String description;
    private int price;
    private String image;
    @NotEmpty
    private String category;
    private long dateAdded;
    private boolean status;
}
