package com.congdinh.recipeapi.dto.user;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is not empty")
    @Length(min = 3, max = 255, message = "First name must be between 3 and 255 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is not empty")
    @Length(min = 3, max = 255, message = "Last name must be between 3 and 255 characters")
    private String lastName;

    @NotNull(message = "User name is required")
    @NotBlank(message = "User name is not empty")
    @Length(min = 3, max = 50, message = "User name must be between 3 and 50 characters")
    private String username;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is not empty")
    @Length(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    private String email;
}
