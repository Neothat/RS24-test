package ru.rs24.restapi.mappers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о категории")
public class CategoryDto {
    @Schema(description = "id", required = true)
    private long id;
    @NotEmpty
    @Schema(description = "Имя", required = true)
    private String name;
    @Schema(description = "Краткое описание")
    private String shortDescription;

    public CategoryDto(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

}
