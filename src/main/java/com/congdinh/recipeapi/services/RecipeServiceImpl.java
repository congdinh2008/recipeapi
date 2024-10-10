package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

import com.congdinh.recipeapi.entities.Recipe;
import com.congdinh.recipeapi.repositories.RecipeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.congdinh.recipeapi.dto.recipe.RecipeCreateDTO;
import com.congdinh.recipeapi.dto.recipe.RecipeDTO;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    // Inject RecipeRepository via constructor
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeDTO> findAll() {
        var recipes = recipeRepository.findAll();

        var recipeDTOs = recipes.stream().map(recipe -> {
            var recipeDTO = new RecipeDTO();
            recipeDTO.setId(recipe.getId());
            recipeDTO.setTitle(recipe.getTitle());
            recipeDTO.setDescription(recipe.getDescription());
            recipeDTO.setImage(recipe.getImage());
            recipeDTO.setPrepTime(recipe.getPrepTime());
            recipeDTO.setCookTime(recipe.getCookTime());
            recipeDTO.setServings(recipe.getServings());
            return recipeDTO;
        }).toList();

        return recipeDTOs;
    }

    @Override
    public List<RecipeDTO> findAll(String keyword) {
        // Find recipe by keyword
        Specification<Recipe> specification = (root, query, criteriaBuilder) -> {
            // Neu keyword null thi tra ve null
            if (keyword == null) {
                return null;
            }

            // Neu keyword khong null
            // WHERE LOWER(name) LIKE %keyword%
            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(name) LIKE %keyword% OR LOWER(description) LIKE %keyword%
            return criteriaBuilder.or(titlePredicate, desPredicate);
        };

        var recipes = recipeRepository.findAll(specification);

        // Covert List<Recipe> to List<RecipeDTO>
        var recipeDTOs = recipes.stream().map(recipe -> {
            var recipeDTO = new RecipeDTO();
            recipeDTO.setId(recipe.getId());
            recipeDTO.setTitle(recipe.getTitle());
            recipeDTO.setDescription(recipe.getDescription());
            recipeDTO.setImage(recipe.getImage());
            recipeDTO.setPrepTime(recipe.getPrepTime());
            recipeDTO.setCookTime(recipe.getCookTime());
            recipeDTO.setServings(recipe.getServings());
            return recipeDTO;
        }).toList();

        return recipeDTOs;
    }

    @Override
    public Page<RecipeDTO> findAll(String keyword, Pageable pageable) {
        // Find recipe by keyword
        Specification<Recipe> specification = (root, query, criteriaBuilder) -> {
            // Neu keyword null thi tra ve null
            if (keyword == null) {
                return null;
            }

            // Neu keyword khong null
            // WHERE LOWER(name) LIKE %keyword%
            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(name) LIKE %keyword% OR LOWER(description) LIKE %keyword%
            return criteriaBuilder.or(titlePredicate, desPredicate);
        };

        var recipes = recipeRepository.findAll(specification, pageable);

        // Covert Page<Recipe> to Page<RecipeDTO>
        var recipeDTOs = recipes.map(recipe -> {
            var recipeDTO = new RecipeDTO();
            recipeDTO.setId(recipe.getId());
            recipeDTO.setTitle(recipe.getTitle());
            recipeDTO.setDescription(recipe.getDescription());
            recipeDTO.setImage(recipe.getImage());
            recipeDTO.setPrepTime(recipe.getPrepTime());
            recipeDTO.setCookTime(recipe.getCookTime());
            recipeDTO.setServings(recipe.getServings());

            return recipeDTO;
        });

        return recipeDTOs;
    }

    @Override
    public RecipeDTO findById(UUID id) {
        var recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            return null;
        }

        var recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipe.getId());
        recipeDTO.setTitle(recipe.getTitle());
        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setImage(recipe.getImage());
        recipeDTO.setPrepTime(recipe.getPrepTime());
        recipeDTO.setCookTime(recipe.getCookTime());
        recipeDTO.setServings(recipe.getServings());

        return recipeDTO;
    }

    @Override
    public RecipeDTO create(RecipeCreateDTO recipeCreateDTO) {
        // Kiem tra recipeDTO null
        if (recipeCreateDTO == null) {
            throw new IllegalArgumentException("Recipe is required");
        }

        // Checl if recipe name is existed
        var existedRecipe = recipeRepository.findByTitle(recipeCreateDTO.getTitle());
        if (existedRecipe != null) {
            throw new IllegalArgumentException("Recipe name is existed");
        }

        // Convert RecipeDTO to Recipe
        var recipe = new Recipe();
        recipe.setTitle(recipeCreateDTO.getTitle());
        recipe.setDescription(recipeCreateDTO.getDescription());
        recipe.setImage(recipeCreateDTO.getImage());
        recipe.setPrepTime(recipeCreateDTO.getPrepTime());
        recipe.setCookTime(recipeCreateDTO.getCookTime());
        recipe.setServings(recipeCreateDTO.getServings());

        // Save recipe
        recipe = recipeRepository.save(recipe);

        // Convert Recipe to RecipeDTO
        var newRecipeDTO = new RecipeDTO();
        newRecipeDTO.setId(recipe.getId());
        newRecipeDTO.setTitle(recipe.getTitle());
        newRecipeDTO.setDescription(recipe.getDescription());
        newRecipeDTO.setImage(recipe.getImage());
        newRecipeDTO.setPrepTime(recipe.getPrepTime());
        newRecipeDTO.setCookTime(recipe.getCookTime());
        newRecipeDTO.setServings(recipe.getServings());

        return newRecipeDTO;
    }

    @Override
    public RecipeDTO update(UUID id, RecipeDTO recipeDTO) {
        if (recipeDTO == null) {
            throw new IllegalArgumentException("Recipe is required");
        }

        // Checl if recipe name is existed
        var existedRecipe = recipeRepository.findByTitle(recipeDTO.getTitle());
        if (existedRecipe != null && !existedRecipe.getId().equals(id)) {
            throw new IllegalArgumentException("Recipe name is existed");
        }

        // Find recipe by id - Managed
        var recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            throw new IllegalArgumentException("Recipe not found");
        }

        // Update recipe
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());

        // Save recipe => update
        recipe = recipeRepository.save(recipe);

        // Convert Recipe to RecipeDTO
        var updatedRecipeDTO = new RecipeDTO();
        updatedRecipeDTO.setId(recipe.getId());
        updatedRecipeDTO.setTitle(recipe.getTitle());
        updatedRecipeDTO.setDescription(recipe.getDescription());
        updatedRecipeDTO.setImage(recipe.getImage());
        updatedRecipeDTO.setPrepTime(recipe.getPrepTime());
        updatedRecipeDTO.setCookTime(recipe.getCookTime());
        updatedRecipeDTO.setServings(recipe.getServings());

        return updatedRecipeDTO;
    }

    @Override
    public boolean delete(UUID id) {
        // Find recipe by id - Managed
        var recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            throw new IllegalArgumentException("Recipe not found");
        }

        // Delete recipe
        recipeRepository.delete(recipe);

        // Check if recipe is deleted
        return !recipeRepository.existsById(id);
    }
}
