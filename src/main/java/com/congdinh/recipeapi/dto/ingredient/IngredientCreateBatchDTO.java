package com.congdinh.recipeapi.dto.ingredient;

import lombok.*;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCreateBatchDTO {

    // Validate list of ingredients
    @NotEmpty(message = "List of ingredients is required")
    private List<IngredientCreateDTO> ingredients;
}
