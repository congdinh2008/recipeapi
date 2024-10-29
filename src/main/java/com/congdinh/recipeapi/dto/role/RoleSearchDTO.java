package com.congdinh.recipeapi.dto.role;

import com.congdinh.recipeapi.dto.core.SearchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSearchDTO extends SearchDTO {
    private String keyword;
}
