package my.project.clientProcessing.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DocumentIsBannedException extends RuntimeException {

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public DocumentIsBannedException(String message) {
        super(message);
    }
}
