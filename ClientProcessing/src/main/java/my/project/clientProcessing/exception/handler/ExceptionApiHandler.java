package my.project.clientProcessing.exception.handler;

import my.project.clientProcessing.dto.ErrorMessageDto;
import my.project.clientProcessing.exception.AlreadyExistException;
import my.project.clientProcessing.exception.DocumentIsBannedException;
import my.project.clientProcessing.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionApiHandler {

    // документ находится в черном списке
    @ExceptionHandler(DocumentIsBannedException.class)
    public ResponseEntity<ErrorMessageDto> handleDocumentIsBannedException(DocumentIsBannedException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorMessageDto(ex.getMessage()));
    }

    // нарушение уникальности данных (уже существует клиент с таким email и тд)
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorMessageDto> handleAlreadyExistException(AlreadyExistException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorMessageDto(ex.getMessage()));
    }

    // запрашиваемые данные не найдены
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorMessageDto(ex.getMessage()));
    }

    // ошибка валиадции запроса
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(fe -> fe.getDefaultMessage() != null)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    // ошибка валидации enum в запросе
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEnum(HttpMessageNotReadableException ex) {

        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatEx) {
            String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
            Class<?> targetType = invalidFormatEx.getTargetType();

            if (targetType.isEnum()) {
                Object[] allowedValues = targetType.getEnumConstants();
                errors.put(fieldName, "Недопустимое значение. Допустимые значения: " +
                        Arrays.stream(allowedValues).map(Object::toString).toList());
            } else {
                errors.put(fieldName, "Недопустимое значение");
            }
        } else {
            errors.put("error", "Неверный формат запроса");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
