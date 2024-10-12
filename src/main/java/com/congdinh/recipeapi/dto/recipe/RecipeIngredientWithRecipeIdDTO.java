package com.congdinh.recipeapi.dto.recipe;

import java.util.List;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientWithRecipeIdDTO {
    private UUID recipeId;
    private List<RecipeIngredientDTO> ingredients;
}