package com.thiago.ecommerce.repository;

import com.thiago.ecommerce.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
