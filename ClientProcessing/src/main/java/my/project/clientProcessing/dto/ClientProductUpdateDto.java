package my.project.clientProcessing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import my.lib.core.StatusEnum;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientProductUpdateDto(
        @JsonProperty(required = false)
        LocalDate closeDate,

        @JsonProperty(required = false)
        StatusEnum status
) {}
