package my.project.accountProcessing.dto;

import java.math.BigDecimal;

public record PaymentCreateDto(

        BigDecimal amount

) {}
