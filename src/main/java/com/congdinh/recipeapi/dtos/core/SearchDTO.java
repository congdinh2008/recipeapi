package com.congdinh.recipeapi.dtos.core;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private String sortBy;

    private SortDirection order;

    @PositiveOrZero(message = "Page must be greater than or equal to 0")
    private Integer page;

    @Positive(message = "Size must be greater than 0")
    private Integer size;
}
