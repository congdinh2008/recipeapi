package com.congdinh.recipeapi.dto.recipe;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {
    private UUID ingredientId;

    private String name;
    
    private String amount;
}
