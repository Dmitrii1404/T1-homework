package my.project.creditProcessing.dto;

import my.project.creditProcessing.entity.productRegistry.ProductRegistry;

import java.math.BigDecimal;
import java.util.List;

public record ClientPaymentCheckDto(

        BigDecimal totalSumCredits,
        boolean hasExpired,
        List<ProductRegistry> productRegistries

) {}
