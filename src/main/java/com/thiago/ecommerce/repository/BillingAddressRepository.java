package com.thiago.ecommerce.repository;

import com.thiago.ecommerce.entity.BillingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingAddressRepository extends JpaRepository<BillingAddressEntity, Long> {
}
