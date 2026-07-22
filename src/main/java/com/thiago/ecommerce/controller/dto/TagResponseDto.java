package com.thiago.ecommerce.controller.dto;

import com.thiago.ecommerce.entity.TagEntity;

public record TagResponseDto(Long tagId,
                             String name) {

    public static TagResponseDto fromEntity(TagEntity entity) {
        return new TagResponseDto(entity.getTagId(), entity.getName());
    }
}
