package my.project.clientProcessing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import my.project.clientProcessing.entity.client.DocumentType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientCreateDto(

        String firstName,

        @JsonProperty(required = false)
        String middleName,

        @JsonProperty(required = false)
        String lastName,

        @JsonProperty(required = false)
        LocalDate dateOfBirth,

        DocumentType documentType,
        String documentId,

        @JsonProperty(required = false)
        String documentPrefix,

        @JsonProperty(required = false)
        String documentSuffix,

        String login,
        String email,
        String password

){}
