package com.congdinh.recipeapi.controllers;

import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.congdinh.recipeapi.dto.category.CategoryCreateDTO;
import com.congdinh.recipeapi.dto.category.CategoryDTO;
import com.congdinh.recipeapi.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final PagedResourcesAssembler<CategoryDTO> pagedResourcesAssembler;

    public CategoryController(CategoryService categoryService,
            PagedResourcesAssembler<CategoryDTO> pagedResourcesAssembler) {
        this.categoryService = categoryService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // Get all - GetMapping - /api/v1/categories
    // Search - GetMapping - /api/v1/categories?keyword=...&page=...&size=...
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy, // Xac dinh truong sap xep
            @RequestParam(required = false, defaultValue = "asc") String order, // Xac dinh chieu sap xep
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "2") Integer size) {
        // Check sort order
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        // Search category by keyword and paging
        var categories = categoryService.findAll(keyword, pageable);

        // Convert to PagedModel - Enhance data with HATEOAS - Easy to navigate with
        // links
        var pagedModel = pagedResourcesAssembler.toModel(categories);

        return ResponseEntity.ok(pagedModel);
    }

    // Get by id - GetMapping - /api/v1/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var category = categoryService.findById(id);
        // Check if category is null => return 404 Not Found
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if category is not null => return 200 OK with category
        return ResponseEntity.ok(category);
    }

    // Create - PostMapping - /api/v1/categories
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO,
            BindingResult bindingResult) {
        // Validate categoryCreateDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newCategory = categoryService.create(categoryCreateDTO);

        // Check if newCategory is null => return 400 Bad Request
        if (newCategory == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if newCategory is not null => return 201 Created with newCategory
        return ResponseEntity.status(201).body(newCategory);
    }

    // Update - PutMapping - /api/v1/categories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {
        // Validate categoryDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedCategoryDTO = categoryService.update(id, categoryDTO);

        // Check if updatedCategory is null => return 400 Bad Request
        if (updatedCategoryDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if updatedCategory is not null => return 201 Created with
        // updatedCategory
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    // Delete - DeleteMapping - /api/v1/categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        var existedCategory = categoryService.findById(id);
        // Check if category is null => return 404 Not Found
        if (existedCategory == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if category is not null => delete category
        var isDeleted = categoryService.delete(id);

        return ResponseEntity.ok(isDeleted);
    }
}
