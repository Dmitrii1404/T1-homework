package my.project.clientProcessing.dto;

import my.project.clientProcessing.entity.product.ProductKey;

public record ProductResponseDto(
        Long id,
        String name,
        ProductKey key,
        String productId
) {}
