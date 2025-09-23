package my.project.clientProcessing.dto;

import jakarta.validation.constraints.NotNull;

public record ClientProductCreateDto(

        @NotNull
        Long clientId,

        @NotNull
        Long productId

) {}
