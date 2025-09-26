package my.project.creditProcessing.exception.NonRetryable;

import my.project.creditProcessing.exception.NonRetryableException;

public class CreditLimitExceedException extends NonRetryableException {
    public CreditLimitExceedException(String message) {
        super(message);
    }
}
