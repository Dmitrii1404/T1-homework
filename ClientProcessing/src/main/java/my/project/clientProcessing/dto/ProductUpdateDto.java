package my.project.clientProcessing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import my.project.clientProcessing.entity.product.ProductKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductUpdateDto(

        @JsonProperty(required = false)
        String name,

        @JsonProperty(required = false)
        ProductKey key

) {}
