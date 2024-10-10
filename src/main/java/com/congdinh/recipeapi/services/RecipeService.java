package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.congdinh.recipeapi.dto.recipe.RecipeAddIngredientDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeCreateDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeEditDTO;

public interface RecipeService {
    List<RecipeDTO> findAll();

    List<RecipeDTO> findAll(String keyword);

    Page<RecipeDTO> findAll(String keyword, Pageable pageable);

    RecipeDTO findById(UUID id);

    RecipeDTO create(RecipeCreateDTO recipeCreateDTO);

    RecipeDTO update(UUID id, RecipeEditDTO recipeEditDTO);

    boolean delete(UUID id);

    boolean addIngredient(UUID id, UUID ingredientId, RecipeAddIngredientDTO recipeAddIngredientDTO);
}
