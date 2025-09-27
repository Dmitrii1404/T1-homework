package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

public record ClientProductAccountCreateDto(

        @NotNull(message = "Id клиента обязательно")
        Long clientId,

        @NotNull(message = "Id продукта обязательно")
        Long productId

) {}
