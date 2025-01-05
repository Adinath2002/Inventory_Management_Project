package com.project.inventory.dtos;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Username is required.")
    @Size(min = 3, message = "Username must be at least 3 characters long.")
    private String username;

    @NotEmpty(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    @NotEmpty(message = "Role is required.")
    @Pattern(regexp = "ADMIN|USER", message = "Role should be either ADMIN or CUSTOMER.")
    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"
}
