package com.project.inventory.services;

import com.project.inventory.dtos.OrderDTO;
import com.project.inventory.model.Order;
import com.project.inventory.model.User;
import com.project.inventory.model.Product;
import com.project.inventory.repository.OrderRepository;
import com.project.inventory.repository.UserRepository;
import com.project.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = orderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        log.info("Fetched {} orders", orders.size());
        return orders;
    }

    public OrderDTO getOrderById(Long id) {
        log.info("Fetching order by ID: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        log.info("Order fetched successfully with ID: {}", id);
        return toDTO(order);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("Creating a new order for user ID: {}", orderDTO.getUserId());
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = orderDTO.getProductIds().stream().map(productId -> productRepository
                .findById(productId).orElseThrow(() -> new RuntimeException("Product not found")))
                .collect(Collectors.toList());

        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setQuantity(orderDTO.getQuantity());
        order = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", order.getId());
        return toDTO(order);
    }

    public void deleteOrder(Long id) {
        log.info("Deleting order by ID: {}", id);
        if (!orderRepository.existsById(id)) {
            log.error("Order with ID {} not found", id);
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
        log.info("Order with ID {} deleted successfully", id);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        log.info("Updating order with ID: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderDTO.getUserId() != null) {
            User user = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        if (orderDTO.getProductIds() != null && !orderDTO.getProductIds().isEmpty()) {
            List<Product> products = orderDTO.getProductIds().stream().map(productId -> productRepository
                    .findById(productId).orElseThrow(() -> new RuntimeException("Product not found")))
                    .collect(Collectors.toList());
            order.setProducts(products);
        }

        if (orderDTO.getQuantity() > 0) {
            order.setQuantity(orderDTO.getQuantity());
        }

        order = orderRepository.save(order);
        log.info("Order updated successfully with ID: {}", order.getId());
        return toDTO(order);
    }

    private OrderDTO toDTO(Order order) {
        log.info("Converting Order entity to DTO for order ID: {}", order.getId());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setProductIds(order.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        log.info("Converted Order entity to DTO for order ID: {}", order.getId());
        return orderDTO;
    }
}
