package com.congdinh.recipeapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.congdinh.recipeapi.entities.RecipeIngredient;
import com.congdinh.recipeapi.entities.RecipeIngredientId;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
    void deleteByRecipeId(UUID id);
}
