package my.project.accountProcessing.exception.NonRetryable;

import my.project.accountProcessing.exception.NonRetryableException;
import org.springframework.http.HttpStatus;

public class NotFountException extends NonRetryableException {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public NotFountException(String message) {
        super(message);
    }
}
