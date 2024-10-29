package com.congdinh.recipeapi.dto.category;

import com.congdinh.recipeapi.dto.core.SearchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDTO extends SearchDTO {
    private String keyword;
}
