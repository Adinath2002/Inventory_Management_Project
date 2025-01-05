package com.project.inventory.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Positive
    private int quantity;

    @Positive
    private double price;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    // Constructor with ID
    public Product(Long id) {
        this.id = id;
    }

    // Default constructor
    public Product() {}
}
