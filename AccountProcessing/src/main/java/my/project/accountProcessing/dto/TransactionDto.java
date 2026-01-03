package my.project.accountProcessing.dto;

import my.lib.core.TransactionType;

import java.math.BigDecimal;

public record TransactionDto(

        Long cardId,
        TransactionType type,
        BigDecimal amount

) {}
