package com.project.inventory.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Username is required.")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "Password is required.")
    private String password;

    @Column(nullable = false)
    @NotEmpty(message = "Role is required.")
    private String role; // e.g., ROLE_ADMIN or ROLE_USER

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    // Constructor with ID
    public User(Long id) {
        this.id = id;
    }

    // Default constructor
    public User() {}
}
