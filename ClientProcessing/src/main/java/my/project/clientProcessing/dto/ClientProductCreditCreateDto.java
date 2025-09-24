package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ClientProductCreditCreateDto (

        @NotNull
        Long clientId,

        @NotNull
        Long productId,

        @NotNull
        Integer monthCount,

        @NotNull
        BigDecimal creditAmount

) {}
