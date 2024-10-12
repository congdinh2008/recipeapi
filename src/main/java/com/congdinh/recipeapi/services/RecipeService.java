package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.congdinh.recipeapi.dto.recipe.RecipeAddIngredientDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeAddListIngredientDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeCreateDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeEditDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeIngredientWithRecipeIdDTO;

public interface RecipeService {
    List<RecipeDTO> findAll();

    List<RecipeDTO> findAll(String keyword);

    Page<RecipeDTO> findAll(String keyword, Pageable pageable);

    Page<RecipeDTO> findAll(String keyword, String categoryName, Pageable pageable);

    RecipeDTO findById(UUID id);

    RecipeDTO create(RecipeCreateDTO recipeCreateDTO);

    RecipeDTO update(UUID id, RecipeEditDTO recipeEditDTO);

    boolean delete(UUID id);

    boolean addIngredient(UUID id, RecipeAddIngredientDTO recipeAddIngredientDTO);

    RecipeIngredientWithRecipeIdDTO addIngredient(UUID id, RecipeAddListIngredientDTO recipeAddListIngredientDTO);
}
