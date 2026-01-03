package my.project.accountProcessing.exception.NonRetryable;

import my.project.accountProcessing.exception.NonRetryableException;

public class InsufficientFundsException extends NonRetryableException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
