package com.congdinh.recipeapi.dto.recipe;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeAddListIngredientDTO {
    @NotEmpty(message = "Ingredients is not empty")
    private List<RecipeAddIngredientDTO> ingredients;
}
