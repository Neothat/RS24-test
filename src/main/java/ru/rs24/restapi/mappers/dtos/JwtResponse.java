package ru.rs24.restapi.mappers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Bearer Token")
public class JwtResponse {
    @Schema(description = "Token", required = true)
    private String token;
}
