package com.project.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.inventory.dtos.OrderDTO;
import com.project.inventory.model.User;
import com.project.inventory.services.OrderService;
import com.project.inventory.services.UserService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping
	public List<OrderDTO> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
		OrderDTO orderDTO = orderService.getOrderById(id);
		return ResponseEntity.ok(orderDTO);
	}

	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
		OrderDTO newOrder = orderService.createOrder(orderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
		orderService.deleteOrder(id);
		String message = "Order deleted with ID: " + id;
		return ResponseEntity.ok(message);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
		try {
			OrderDTO updatedOrder = orderService.updateOrder(orderDTO, id);
			String message = "These details are updated for the order number: " + id + "\n" + "User ID: "
					+ (updatedOrder.getUserId() != null ? updatedOrder.getUserId() : "unchanged") + "\n"
					+ "Product IDs: "
					+ (updatedOrder.getProductIds() != null ? updatedOrder.getProductIds() : "unchanged") + "\n"
					+ "Quantity: " + updatedOrder.getQuantity() + "\n" + "Order Date: " + updatedOrder.getOrderDate()
					+ "\n" + "Total Amount: " + updatedOrder.getTotalAmount();
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			e.printStackTrace(); // Print stack trace to console
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	 
}
