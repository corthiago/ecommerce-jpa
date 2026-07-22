package com.thiago.ecommerce.controller.dto;

import java.math.BigDecimal;

public record OrderItemDto(Integer quantity,
                           Long productId) {
}
