package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;
import my.lib.core.TransactionType;

import java.math.BigDecimal;

public record TransactionCreateDto(

        @NotNull(message = "Тип транзакции обязателен")
        TransactionType type,

        @NotNull(message = "Сумма транзакции обязательна")
        BigDecimal amount

) {}
