package my.project.creditProcessing.dto;

import java.math.BigDecimal;

public record CreditCreateDto(

        Long clientId,
        Long productId,
        Integer monthCount,
        BigDecimal creditAmount

) {}
