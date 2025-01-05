package com.project.inventory.services;

import org.springframework.stereotype.Service;

import com.project.inventory.dtos.ProductDTO;
import com.project.inventory.exceptions.ResourceNotFoundException;
import com.project.inventory.model.Product;
import com.project.inventory.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product saveProduct(ProductDTO productDTO) {

		log.info("Saving new product: {}", productDTO.getName());

		Product product = new Product();
		product.setName(productDTO.getName());
		product.setQuantity(productDTO.getQuantity());
		product.setPrice(productDTO.getPrice());
		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {

		log.info("Fetching all products");

		return productRepository.findAll();
	}

	public Product getProductById(Long id) {

		log.info("Fetching product by ID: {}", id);

		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
	}

	public void deleteProduct(Long id) {

		log.info("Deleting product by ID: {}", id);

		productRepository.deleteById(id);

		log.info("Product deleted successfully with ID: {}", id);
	}

	public Product updateProduct(ProductDTO productDTO, Long id) { 
		log.info("Updating product with ID: {}", id); 
		Product product = productRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id)); 
		product.setName(productDTO.getName()); 
		product.setQuantity(productDTO.getQuantity()); 
		product.setPrice(productDTO.getPrice()); 
		Product updatedProduct = productRepository.save(product); 
		log.info("Product updated successfully with ID: {}", updatedProduct.getId()); return updatedProduct;
	}

	public ProductDTO toDTO(Product product) {

		log.info("Converting Product entity to DTO for product ID: {}", product.getId());

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setPrice(product.getPrice());
		return productDTO;
	}
}
