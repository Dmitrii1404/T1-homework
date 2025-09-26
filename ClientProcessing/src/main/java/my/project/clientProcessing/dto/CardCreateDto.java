package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;
import my.lib.core.PaymentSystem;

public record CardCreateDto(

        @NotNull(message = "Тип платежной системы обязателен")
        PaymentSystem paymentSystem

) {}
