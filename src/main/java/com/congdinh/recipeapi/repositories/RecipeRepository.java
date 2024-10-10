package com.congdinh.recipeapi.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;

import com.congdinh.recipeapi.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    Recipe findByTitle(String title);
}
