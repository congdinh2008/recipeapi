package com.congdinh.recipeapi.dto.recipe;

import org.hibernate.validator.constraints.Length;
import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEditDTO {
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

    private UUID categoryId;

    // private List<RecipeAddIngredientDTO> ingredients;
}
