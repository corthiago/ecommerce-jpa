package com.thiago.ecommerce.repository;

import com.thiago.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
