package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ClientProductCreditCreateDto(

        @NotNull(message = "Id клиента обязательно")
        Long clientId,

        @NotNull(message = "Id продукта обязательно")
        Long productId,

        @NotNull(message = "Укажите количество месяцев, на которое открывается кредит")
        Integer monthCount,

        @NotNull(message = "Укажите сумму кредита")
        BigDecimal creditAmount

) {}
