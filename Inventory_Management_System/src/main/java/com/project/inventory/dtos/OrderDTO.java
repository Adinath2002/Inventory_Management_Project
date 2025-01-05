package com.project.inventory.dtos;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    private Long userId;

    private List<Long> productIds;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    private LocalDateTime orderDate;

    @PositiveOrZero(message = "Total amount must be zero or positive.")
    private double totalAmount;
}