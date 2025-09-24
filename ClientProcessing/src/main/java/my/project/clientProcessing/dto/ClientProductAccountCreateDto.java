package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

public record ClientProductAccountCreateDto(

        @NotNull
        Long clientId,

        @NotNull
        Long productId

) {}
