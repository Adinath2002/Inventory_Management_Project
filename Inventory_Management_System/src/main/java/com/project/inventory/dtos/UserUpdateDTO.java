package com.project.inventory.dtos;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateDTO {
    private Long id;

    @Size(min = 3, message = "Username must be at least 3 characters long.")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    @Pattern(regexp = "ADMIN|USER", message = "Role should be either ADMIN or CUSTOMER.")
    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"
}
