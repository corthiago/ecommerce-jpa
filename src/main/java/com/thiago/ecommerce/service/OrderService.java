package com.thiago.ecommerce.service;

import com.thiago.ecommerce.controller.dto.CreateOrderDto;
import com.thiago.ecommerce.controller.dto.OrderItemDto;
import com.thiago.ecommerce.controller.dto.OrderSummaryDto;
import com.thiago.ecommerce.entity.*;
import com.thiago.ecommerce.exception.CreateOrderException;
import com.thiago.ecommerce.repository.OrderItemRepository;
import com.thiago.ecommerce.repository.OrderRepository;
import com.thiago.ecommerce.repository.ProductRepository;
import com.thiago.ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(UserRepository userRepository,
                        OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderEntity createOrder(CreateOrderDto dto){

        var order = new OrderEntity();

        // 1. Validate user
        var user = validateUser(dto);

        // 2. Validate order items
        var orderItems = validateOrderItems(order, dto);

        // 3. Calculate order total
        var orderTotal = calculateOrderTotal(orderItems);

        // 4. Repository save
        order.setUser(user);
        order.setItems(orderItems);
        order.setTotal(orderTotal);
        order.setDate(LocalDateTime.now());

        return orderRepository.save(order);

    }

    private UserEntity validateUser(CreateOrderDto dto){
        return userRepository.findById(dto.userId())
                .orElseThrow(() -> new CreateOrderException("User not found"));
    }

    private List<OrderItemEntity> validateOrderItems(OrderEntity order, CreateOrderDto dto){

        if(dto.items().isEmpty()) {
            throw new CreateOrderException("Order items are empty");
        }

        return dto.items().stream()
                .map(orderItemDto -> getOrderItem(order, orderItemDto))
                .toList();
    }

    private OrderItemEntity getOrderItem(OrderEntity order, OrderItemDto orderItemDto) {
        var orderItemEntity = new OrderItemEntity();
        var id = new OrderItemId();
        var product = getProduct(orderItemDto.productId());

        id.setOrder(order);
        id.setProduct(product);

        orderItemEntity.setId(id);
        orderItemEntity.setQuantity(orderItemDto.quantity());
        orderItemEntity.setSalePrice(product.getPrice());

        return orderItemEntity;
    }

    private ProductEntity getProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new CreateOrderException("Product " + productId + " not found"));
    }

    private BigDecimal calculateOrderTotal(List<OrderItemEntity> orderItems) {
        return orderItems.stream()
                .map(item -> item.getSalePrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Page<OrderSummaryDto> findAll(Integer page, Integer pageSize) {
        return orderRepository.findAll(PageRequest.of(page, pageSize))
                .map(entity -> {
                    return new OrderSummaryDto(
                            entity.getOrderId(),
                            entity.getDate(),
                            entity.getUser().getUserId(),
                            entity.getTotal());
                });
    }

    public Optional<OrderEntity> findById(Long orderId){
        return orderRepository.findById(orderId);
    }
}
