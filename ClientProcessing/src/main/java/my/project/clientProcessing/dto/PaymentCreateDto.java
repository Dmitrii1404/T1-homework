package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentCreateDto(

        @NotNull(message = "Сумма транзакции обязательна")
        BigDecimal amount

) {}
