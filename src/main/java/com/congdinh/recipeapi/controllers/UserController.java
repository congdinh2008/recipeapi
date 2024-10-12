package com.congdinh.recipeapi.controllers;

import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.congdinh.recipeapi.dto.user.UserCreateDTO;
import com.congdinh.recipeapi.dto.user.UserDTO;
import com.congdinh.recipeapi.dto.user.UserEditDTO;
import com.congdinh.recipeapi.dto.user.UserSearchDTO;
import com.congdinh.recipeapi.dtos.core.SortDirection;
import com.congdinh.recipeapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users", description = "The User API")
public class UserController {
    private final UserService userService;
    private final PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;

    public UserController(UserService userService,
            PagedResourcesAssembler<UserDTO> pagedResourcesAssembler) {
        this.userService = userService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // Get all - GetMapping - /api/v1/users
    // Search - GetMapping - /api/v1/users?keyword=...&page=...&size=...
    @GetMapping
    @Operation(summary = "Get all users or search users by keyword")
    @ApiResponse(responseCode = "200", description = "Return all users or search users by keyword")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "firstName") String sortBy, // Xac dinh truong sap xep
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

        // Search user by keyword and paging
        var users = userService.findAll(keyword, pageable);

        // Convert to PagedModel - Enhance data with HATEOAS - Easy to navigate with
        // links
        var pagedModel = pagedResourcesAssembler.toModel(users);

        return ResponseEntity.ok(pagedModel);
    }

    // Get all - GetMapping - /api/v1/users
    // Search - GetMapping - /api/v1/users?keyword=...&page=...&size=...
    @PostMapping("/search")
    @Operation(summary = "Get all users or search users by keyword")
    @ApiResponse(responseCode = "200", description = "Return all users or search users by keyword")
    public ResponseEntity<?> search(@RequestBody UserSearchDTO userSearchDTO) {
        // Check sort order
        Pageable pageable = null;

        if (userSearchDTO.getOrder().equals(SortDirection.ASC)) {
            pageable = PageRequest.of(userSearchDTO.getPage(), userSearchDTO.getSize(),
                    Sort.by(userSearchDTO.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(userSearchDTO.getPage(), userSearchDTO.getSize(),
                    Sort.by(userSearchDTO.getSortBy()).descending());
        }

        // Search user by keyword and paging
        var users = userService.findAll(userSearchDTO.getKeyword(), pageable);

        // Convert to PagedModel - Enhance data with HATEOAS - Easy to navigate with
        // links
        var pagedModel = pagedResourcesAssembler.toModel(users);

        return ResponseEntity.ok(pagedModel);
    }

    // Get by id - GetMapping - /api/v1/users/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @ApiResponse(responseCode = "200", description = "Return user by id")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var user = userService.findById(id);
        // Check if user is null => return 404 Not Found
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if user is not null => return 200 OK with user
        return ResponseEntity.ok(user);
    }

    // Create - PostMapping - /api/v1/users
    @PostMapping
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "Create new user")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateDTO userCreateDTO,
            BindingResult bindingResult) {
        // Validate userCreateDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newUser = userService.create(userCreateDTO);

        // Check if newUser is null => return 400 Bad Request
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if newUser is not null => return 201 Created with newUser
        return ResponseEntity.status(201).body(newUser);
    }

    // Update - PutMapping - /api/v1/users/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Update user by id")
    @ApiResponse(responseCode = "200", description = "Update user by id")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    public ResponseEntity<?> edit(
            @PathVariable UUID id,
            @Valid @RequestBody UserEditDTO userEditDTO,
            BindingResult bindingResult) {
        // Validate userEditDTO
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedUserDTO = userService.update(id, userEditDTO);

        // Check if updatedUser is null => return 400 Bad Request
        if (updatedUserDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if updatedUser is not null => return 201 Created with
        // updatedUser
        return ResponseEntity.ok(updatedUserDTO);
    }

    // Delete - DeleteMapping - /api/v1/users/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ApiResponse(responseCode = "200", description = "Delete user by id")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        var existedUser = userService.findById(id);
        // Check if user is null => return 404 Not Found
        if (existedUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if user is not null => delete user
        var isDeleted = userService.delete(id);

        return ResponseEntity.ok(isDeleted);
    }
}
