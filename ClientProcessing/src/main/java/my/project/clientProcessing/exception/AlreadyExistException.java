package my.project.clientProcessing.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlreadyExistException extends RuntimeException {

    private final HttpStatus status = HttpStatus.CONFLICT;

    public AlreadyExistException(String message) {
        super(message);
    }
}
