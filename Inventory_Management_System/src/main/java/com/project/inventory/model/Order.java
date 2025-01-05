package com.project.inventory.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`") // Ensure the table name is correctly escaped using backticks
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @NotNull
    private int quantity;

    @NotNull
    private LocalDateTime orderDate;

    private double totalAmount;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
        if (this.products != null) {
            this.totalAmount = this.products.stream().mapToDouble(product -> this.quantity * product.getPrice()).sum();
        }
    }
}
