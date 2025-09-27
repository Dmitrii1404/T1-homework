package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import my.project.clientProcessing.entity.product.ProductKey;

public record ProductCreateDto(

        @NotBlank(message = "Название продукта не может быть путым")
        String name,

        @NotNull(message = "Тип продукта обязателен")
        ProductKey key

) {}
