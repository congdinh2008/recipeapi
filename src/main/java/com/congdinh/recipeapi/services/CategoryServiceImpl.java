package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

import com.congdinh.recipeapi.entities.Category;
import com.congdinh.recipeapi.repositories.CategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.congdinh.recipeapi.dto.category.CategoryCreateDTO;
import com.congdinh.recipeapi.dto.category.CategoryDTO;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    // Inject CategoryRepository via constructor
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        var categories = categoryRepository.findAll();

        var categoryDTOs = categories.stream().map(category -> {
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            return categoryDTO;
        }).toList();

        return categoryDTOs;
    }

    @Override
    public List<CategoryDTO> findAll(String keyword) {
        // Find category by keyword
        Specification<Category> specification = (root, query, criteriaBuilder) -> {
            // Neu keyword null thi tra ve null
            if (keyword == null) {
                return null;
            }

            // Neu keyword khong null
            // WHERE LOWER(name) LIKE %keyword%
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(name) LIKE %keyword% OR LOWER(description) LIKE %keyword%
            return criteriaBuilder.or(namePredicate, desPredicate);
        };

        var categories = categoryRepository.findAll(specification);

        // Covert List<Category> to List<CategoryDTO>
        var categoryDTOs = categories.stream().map(category -> {
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            return categoryDTO;
        }).toList();

        return categoryDTOs;
    }

    @Override
    public Page<CategoryDTO> findAll(String keyword, Pageable pageable) {
        // Find category by keyword
        Specification<Category> specification = (root, query, criteriaBuilder) -> {
            // Neu keyword null thi tra ve null
            if (keyword == null) {
                return null;
            }

            // Neu keyword khong null
            // WHERE LOWER(name) LIKE %keyword%
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(name) LIKE %keyword% OR LOWER(description) LIKE %keyword%
            return criteriaBuilder.or(namePredicate, desPredicate);
        };

        var categories = categoryRepository.findAll(specification, pageable);

        // Covert Page<Category> to Page<CategoryDTO>
        var categoryDTOs = categories.map(category -> {
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            return categoryDTO;
        });

        return categoryDTOs;
    }

    @Override
    public CategoryDTO findById(UUID id) {
        var category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            return null;
        }

        var categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        return categoryDTO;
    }

    @Override
    public CategoryDTO create(CategoryCreateDTO categoryCreateDTO) {
        // Kiem tra categoryDTO null
        if (categoryCreateDTO == null) {
            throw new IllegalArgumentException("Category is required");
        }

        // Checl if category name is existed
        var existedCategory = categoryRepository.findByName(categoryCreateDTO.getName());
        if (existedCategory != null) {
            throw new IllegalArgumentException("Category name is existed");
        }

        // Convert CategoryDTO to Category
        var category = new Category();
        category.setName(categoryCreateDTO.getName());
        category.setDescription(categoryCreateDTO.getDescription());

        // Save category
        category = categoryRepository.save(category);

        // Convert Category to CategoryDTO
        var newCategoryDTO = new CategoryDTO();
        newCategoryDTO.setId(category.getId());
        newCategoryDTO.setName(category.getName());
        newCategoryDTO.setDescription(category.getDescription());

        return newCategoryDTO;
    }

    @Override
    public CategoryDTO update(UUID id, CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category is required");
        }

        // Checl if category name is existed
        var existedCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existedCategory != null) {
            throw new IllegalArgumentException("Category name is existed");
        }

        // Find category by id - Managed
        var category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        // Update category
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // Save category => update
        category = categoryRepository.save(category);

        // Convert Category to CategoryDTO
        var updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setId(category.getId());
        updatedCategoryDTO.setName(category.getName());
        updatedCategoryDTO.setDescription(category.getDescription());

        return updatedCategoryDTO;
    }

    @Override
    public boolean delete(UUID id) {
        // Find category by id - Managed
        var category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        // Delete category
        categoryRepository.delete(category);

        // Check if category is deleted
        return !categoryRepository.existsById(id);
    }
}
