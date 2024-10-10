package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.congdinh.recipeapi.dto.ingredient.IngredientCreateBatchDTO;
import com.congdinh.recipeapi.dto.ingredient.IngredientCreateDTO;
import com.congdinh.recipeapi.dto.ingredient.IngredientDTO;

public interface IngredientService {
    List<IngredientDTO> findAll();

    List<IngredientDTO> findAll(String keyword);

    Page<IngredientDTO> findAll(String keyword, Pageable pageable);

    IngredientDTO findById(UUID id);

    IngredientDTO create(IngredientCreateDTO ingredientCreateDTO);

    List<IngredientDTO> create(IngredientCreateBatchDTO ingredientCreateBatchDTO);

    IngredientDTO update(UUID id, IngredientDTO ingredientDTO);

    boolean delete(UUID id);
}
