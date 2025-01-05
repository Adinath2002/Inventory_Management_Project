package com.project.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

