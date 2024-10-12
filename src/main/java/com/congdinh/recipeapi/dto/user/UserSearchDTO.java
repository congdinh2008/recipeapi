package com.congdinh.recipeapi.dto.user;

import com.congdinh.recipeapi.dtos.core.SearchDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO extends SearchDTO {
    private String keyword;
}
