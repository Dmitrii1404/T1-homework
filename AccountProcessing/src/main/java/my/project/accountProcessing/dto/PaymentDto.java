package my.project.accountProcessing.dto;

import java.math.BigDecimal;

public record PaymentDto(

        Long cardId,
        BigDecimal amount

) {}
