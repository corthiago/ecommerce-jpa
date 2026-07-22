package com.thiago.ecommerce.controller;

import com.thiago.ecommerce.controller.dto.ApiResponse;
import com.thiago.ecommerce.controller.dto.CreateOrderDto;
import com.thiago.ecommerce.controller.dto.OrderSummaryDto;
import com.thiago.ecommerce.controller.dto.PaginationResponseDto;
import com.thiago.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDto dto){

        var order = orderService.createOrder(dto);

        return ResponseEntity.created(URI.create("/orders/" + order.getOrderId())).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<OrderSummaryDto>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        var ordersResponse = orderService.findAll(page, pageSize);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ordersResponse.getContent(),
                        new PaginationResponseDto(
                                ordersResponse.getNumber(),
                                ordersResponse.getSize(),
                                ordersResponse.getTotalElements(),
                                ordersResponse.getTotalPages())));
    }
}
