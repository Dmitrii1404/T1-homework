package my.project.clientProcessing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import my.project.clientProcessing.entity.client.DocumentType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientCreateDto(

        @NotBlank(message = "Регион не может быть пустым")
        @Pattern(regexp = "\\d{2}", message = "Регион должен состоять из 2 цифр")
        String region,

        @NotBlank(message = "Отделение банка не может быть пустым")
        @Pattern(regexp = "\\d{2}", message = "Отделение банка должно состоять из 2 цифр")
        String bank,

        @NotBlank(message = "Имя не может быть путым")
        String firstName,

        @JsonProperty(required = false)
        String middleName,

        @JsonProperty(required = false)
        String lastName,

        @JsonProperty(required = false)
        LocalDate dateOfBirth,

        @NotNull(message = "Тип документа обязателен")
        DocumentType documentType,

        @NotBlank(message = "ID документа не может быть пустым")
        String documentId,

        @JsonProperty(required = false)
        String documentPrefix,

        @JsonProperty(required = false)
        String documentSuffix,

        @NotBlank(message = "Логин обязателен")
        String login,

        @Email(message = "Некорректный email")
        @NotBlank(message = "Email обязателен")
        String email,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
        String password

) {}
