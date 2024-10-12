package com.congdinh.recipeapi.dto.ingredient;

import com.congdinh.recipeapi.dtos.core.SearchDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchDTO extends SearchDTO {
    private String keyword;
}
