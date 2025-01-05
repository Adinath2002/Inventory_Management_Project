package com.project.inventory.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String role; 
}
