package com.thiago.ecommerce.repository;

import com.thiago.ecommerce.entity.OrderItemEntity;
import com.thiago.ecommerce.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, OrderItemId> {
}
