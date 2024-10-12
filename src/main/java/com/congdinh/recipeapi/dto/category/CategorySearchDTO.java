package com.congdinh.recipeapi.dto.category;

import com.congdinh.recipeapi.dtos.core.SearchDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDTO extends SearchDTO {
    private String keyword;
}
