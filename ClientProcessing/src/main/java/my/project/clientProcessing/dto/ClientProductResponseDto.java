package my.project.clientProcessing.dto;

import my.lib.core.StatusEnum;

import java.time.LocalDate;

public record ClientProductResponseDto (
        Long clientId,
        Long productId,
        LocalDate openDate,
        LocalDate closeDate,
        StatusEnum status
) {}
