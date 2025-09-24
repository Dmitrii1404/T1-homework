package my.project.accountProcessing.dto;

import my.lib.core.StatusEnum;

public record AccountCreateDto(

        Long clientId,
        Long productId,
        StatusEnum status

) {}
