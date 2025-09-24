package my.project.accountProcessing.exception.NonRetryable;

import org.springframework.http.HttpStatus;

public class SourceNotActiveException extends RuntimeException {
    private final HttpStatus status = HttpStatus.FORBIDDEN;
    public SourceNotActiveException(String message) {
        super(message);
    }
}
