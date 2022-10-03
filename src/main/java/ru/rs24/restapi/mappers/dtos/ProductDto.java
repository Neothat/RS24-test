package ru.rs24.restapi.mappers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о продукте")
public class ProductDto {
    @Schema(description = "id", required = true)
    private long id;
    @NotEmpty
    @Schema(description = "Имя", required = true)
    private String name;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Цена")
    private int price;
    @Schema(description = "Ссылка на изображение")
    private String image;
    @NotEmpty
    @Schema(description = "Категория", required = true)
    private String category;
    @Schema(description = "Дата создания")
    private long dateAdded;
    @Schema(description = "Активность")
    private boolean status;
}
