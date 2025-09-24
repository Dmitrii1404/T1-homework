package my.project.accountProcessing.exception.NonRetryable;

import my.project.accountProcessing.exception.NonRetryableException;
import org.springframework.http.HttpStatus;

public class AlreadyExistException extends NonRetryableException {
    private final HttpStatus httpStatus = HttpStatus.CONFLICT;
    public AlreadyExistException(String message) {
        super(message);
    }
}
