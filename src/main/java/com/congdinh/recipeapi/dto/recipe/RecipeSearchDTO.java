package com.congdinh.recipeapi.dto.recipe;

import com.congdinh.recipeapi.dtos.core.SearchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchDTO extends SearchDTO {
    private String keyword;
    
    private String categoryName;
}
