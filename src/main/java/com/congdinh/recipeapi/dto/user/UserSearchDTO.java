package com.congdinh.recipeapi.dto.user;

import com.congdinh.recipeapi.dto.core.SearchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO extends SearchDTO {
    private String keyword;
}
