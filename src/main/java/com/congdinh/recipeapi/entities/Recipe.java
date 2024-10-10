package com.congdinh.recipeapi.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", unique = true, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "image")
    private String image; // URL to image

    @Column(name = "prep_time", nullable = false, columnDefinition = "INT CHECK (prep_time >= 0) DEFAULT 0")
    private Integer prepTime; // in minutes
    
    @Column(name = "cook_time", nullable = false, columnDefinition = "INT CHECK (cook_time >= 0) DEFAULT 0")
    private Integer cookTime; // in minutes
    
    @Column(name = "servings", nullable = false, columnDefinition = "INT CHECK (servings >= 0) DEFAULT 0")
    private Integer servings; // number of servings

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeIngredient> ingredients;
}
