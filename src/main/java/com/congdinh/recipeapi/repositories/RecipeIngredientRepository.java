package com.congdinh.recipeapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.congdinh.recipeapi.entities.RecipeIngredient;
import com.congdinh.recipeapi.entities.RecipeIngredientId;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
}
