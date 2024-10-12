package com.congdinh.recipeapi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.congdinh.recipeapi.dto.user.UserCreateDTO;
import com.congdinh.recipeapi.dto.user.UserDTO;
import com.congdinh.recipeapi.dto.user.UserEditDTO;

public interface UserService {
    List<UserDTO> findAll();

    List<UserDTO> findAll(String keyword);

    Page<UserDTO> findAll(String keyword, Pageable pageable);

    UserDTO findById(UUID id);

    UserDTO create(UserCreateDTO userCreateDTO);

    UserDTO update(UUID id, UserEditDTO userEditDTO);

    boolean delete(UUID id);
}
