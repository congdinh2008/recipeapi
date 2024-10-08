package com.congdinh.recipeapi.controllers;

import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.congdinh.recipeapi.dto.ingredient.IngredientCreateDTO;
import com.congdinh.recipeapi.dto.ingredient.IngredientDTO;
import com.congdinh.recipeapi.services.IngredientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private final PagedResourcesAssembler<IngredientDTO> pagedResourcesAssembler;

    public IngredientController(IngredientService ingredientService,
            PagedResourcesAssembler<IngredientDTO> pagedResourcesAssembler) {
        this.ingredientService = ingredientService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // Get all - GetMapping - /api/v1/ingredients
    // Search - GetMapping - /api/v1/ingredients?keyword=...&page=...&size=...
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy, // Xac dinh truong sap xep
            @RequestParam(required = false, defaultValue = "asc") String order, // Xac dinh chieu sap xep
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        // Check sort order
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        // Search ingredient by keyword and paging
        var ingredients = ingredientService.findAll(keyword, pageable);

        // Convert to PagedModel - Enhance data with HATEOAS - Easy to navigate with
        // links
        var pagedModel = pagedResourcesAssembler.toModel(ingredients);

        return ResponseEntity.ok(pagedModel);
    }

    // Get by id - GetMapping - /api/v1/ingredients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var ingredient = ingredientService.findById(id);
        // Check if ingredient is null => return 404 Not Found
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if ingredient is not null => return 200 OK with ingredient
        return ResponseEntity.ok(ingredient);
    }

    // Create - PostMapping - /api/v1/ingredients
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody IngredientCreateDTO ingredientCreateDTO,
            BindingResult bindingResult) {
        // Validate ingredientCreateDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newIngredient = ingredientService.create(ingredientCreateDTO);

        // Check if newIngredient is null => return 400 Bad Request
        if (newIngredient == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if newIngredient is not null => return 201 Created with newIngredient
        return ResponseEntity.status(201).body(newIngredient);
    }

    // Update - PutMapping - /api/v1/ingredients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable UUID id,
            @Valid @RequestBody IngredientDTO ingredientDTO,
            BindingResult bindingResult) {
        // Validate ingredientDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedIngredientDTO = ingredientService.update(id, ingredientDTO);

        // Check if updatedIngredient is null => return 400 Bad Request
        if (updatedIngredientDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if updatedIngredient is not null => return 201 Created with
        // updatedIngredient
        return ResponseEntity.ok(updatedIngredientDTO);
    }

    // Delete - DeleteMapping - /api/v1/ingredients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        var existedIngredient = ingredientService.findById(id);
        // Check if ingredient is null => return 404 Not Found
        if (existedIngredient == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if ingredient is not null => delete ingredient
        var isDeleted = ingredientService.delete(id);

        return ResponseEntity.ok(isDeleted);
    }
}