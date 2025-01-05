package com.project.inventory.dtos;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Data
public class ProductDTO {
    private Long id;

    @NotEmpty(message = "Product name is required.")
    private String name;

    @Positive(message = "Quantity must be greater than 0.")
    private int quantity;

    @Positive(message = "Price must be greater than 0.")
    private double price;
}

