package my.project.accountProcessing.dto;

import my.lib.core.PaymentSystem;
import my.lib.core.StatusEnum;

public record CardCreateDto(

        Long accountId,
        PaymentSystem paymentSystem,
        StatusEnum status

) {}
