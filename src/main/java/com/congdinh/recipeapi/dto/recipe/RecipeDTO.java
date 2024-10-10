package com.congdinh.recipeapi.dto.recipe;

import java.util.*;

import org.hibernate.validator.constraints.Length;

import com.congdinh.recipeapi.dto.category.CategoryDTO;
import com.congdinh.recipeapi.dto.ingredient.IngredientDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private UUID id;

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is not empty")
    @Length(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    private String description;

    private String image; // URL to image

    @PositiveOrZero(message = "Preparation time must be a positive number")
    private Integer prepTime; // in minutes

    @PositiveOrZero(message = "Cook time must be a positive number")
    private Integer cookTime; // in minutes

    @PositiveOrZero(message = "Servings must be a positive number")
    private Integer servings; // number of servings

    private CategoryDTO category;

    private List<IngredientDTO> ingredients;
}
