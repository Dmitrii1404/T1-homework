package my.project.creditProcessing.exception.Retryable;

import my.project.creditProcessing.exception.RetryableException;

public class NotFoundUserHttpException extends RetryableException {
    public NotFoundUserHttpException(String message) {
        super(message);
    }
}
