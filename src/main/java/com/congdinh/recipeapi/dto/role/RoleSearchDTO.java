package com.congdinh.recipeapi.dto.role;

import com.congdinh.recipeapi.dtos.core.SearchDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSearchDTO extends SearchDTO {
    private String keyword;
}
