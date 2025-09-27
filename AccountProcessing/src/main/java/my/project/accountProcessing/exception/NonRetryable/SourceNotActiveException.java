package my.project.accountProcessing.exception.NonRetryable;

import org.springframework.http.HttpStatus;

public class SourceNotActiveException extends RuntimeException {
    public SourceNotActiveException(String message) {
        super(message);
    }
}
