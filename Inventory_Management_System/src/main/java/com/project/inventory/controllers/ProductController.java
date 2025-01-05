package com.project.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.inventory.dtos.ProductDTO;
import com.project.inventory.model.Product;
import com.project.inventory.services.ProductService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
		Product product = productService.saveProduct(productDTO);
		return productService.toDTO(product);
	}

	@GetMapping("/")
//	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public List<ProductDTO> getAllProducts() {
		return productService.getAllProducts().stream().map(productService::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ProductDTO getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		return productService.toDTO(product);
	}

//	@DeleteMapping("/{id}")
//	@PreAuthorize("hasAuthority('ADMIN')")
//	public void deleteProduct(@PathVariable Long id) {
//		productService.deleteProduct(id);
//	}

//}
	@DeleteMapping("/{id}") 
	@PreAuthorize("hasAuthority('ADMIN')") 
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) { 
		productService.deleteProduct(id); 
		
		String message = "Product deleted successfully with ID: " + id; 
		
		return ResponseEntity.ok(message);
		
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
		Product updatedProduct = productService.updateProduct(productDTO, id);
		return productService.toDTO(updatedProduct);
	}
}
