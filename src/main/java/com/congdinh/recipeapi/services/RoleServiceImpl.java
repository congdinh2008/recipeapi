package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

import com.congdinh.recipeapi.entities.Role;
import com.congdinh.recipeapi.repositories.RoleRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.congdinh.recipeapi.dto.role.RoleCreateDTO;
import com.congdinh.recipeapi.dto.role.RoleDTO;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    // Inject RoleRepository via constructor
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> findAll() {
        var roles = roleRepository.findAll();

        var roleDTOs = roles.stream().map(role -> {
            var roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roleDTO.setDescription(role.getDescription());
            return roleDTO;
        }).toList();

        return roleDTOs;
    }

    @Override
    public List<RoleDTO> findAll(String keyword) {
        // Find role by keyword
        Specification<Role> specification = (root, query, criteriaBuilder) -> {
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

        var roles = roleRepository.findAll(specification);

        // Covert List<Role> to List<RoleDTO>
        var roleDTOs = roles.stream().map(role -> {
            var roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roleDTO.setDescription(role.getDescription());
            return roleDTO;
        }).toList();

        return roleDTOs;
    }

    @Override
    public Page<RoleDTO> findAll(String keyword, Pageable pageable) {
        // Find role by keyword
        Specification<Role> specification = (root, query, criteriaBuilder) -> {
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

        var roles = roleRepository.findAll(specification, pageable);

        // Covert Page<Role> to Page<RoleDTO>
        var roleDTOs = roles.map(role -> {
            var roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roleDTO.setDescription(role.getDescription());
            return roleDTO;
        });

        return roleDTOs;
    }

    @Override
    public RoleDTO findById(UUID id) {
        var role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            return null;
        }

        var roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());

        return roleDTO;
    }

    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        // Kiem tra roleDTO null
        if (roleCreateDTO == null) {
            throw new IllegalArgumentException("Role is required");
        }

        // Checl if role name is existed
        var existedRole = roleRepository.findByName(roleCreateDTO.getName());
        if (existedRole != null) {
            throw new IllegalArgumentException("Role name is existed");
        }

        // Convert RoleDTO to Role
        var role = new Role();
        role.setName(roleCreateDTO.getName());
        role.setDescription(roleCreateDTO.getDescription());

        // Save role
        role = roleRepository.save(role);

        // Convert Role to RoleDTO
        var newRoleDTO = new RoleDTO();
        newRoleDTO.setId(role.getId());
        newRoleDTO.setName(role.getName());
        newRoleDTO.setDescription(role.getDescription());

        return newRoleDTO;
    }

    @Override
    public RoleDTO update(UUID id, RoleDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role is required");
        }

        // Checl if role name is existed
        var existedRole = roleRepository.findByName(roleDTO.getName());
        if (existedRole != null && !existedRole.getId().equals(id)) {
            throw new IllegalArgumentException("Role name is existed");
        }

        // Find role by id - Managed
        var role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }

        // Update role
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        // Save role => update
        role = roleRepository.save(role);

        // Convert Role to RoleDTO
        var updatedRoleDTO = new RoleDTO();
        updatedRoleDTO.setId(role.getId());
        updatedRoleDTO.setName(role.getName());
        updatedRoleDTO.setDescription(role.getDescription());

        return updatedRoleDTO;
    }

    @Override
    public boolean delete(UUID id) {
        // Find role by id - Managed
        var role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }

        // Delete role
        roleRepository.delete(role);

        // Check if role is deleted
        return !roleRepository.existsById(id);
    }
}
