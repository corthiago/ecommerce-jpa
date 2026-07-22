package com.thiago.ecommerce.repository;

import com.thiago.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
