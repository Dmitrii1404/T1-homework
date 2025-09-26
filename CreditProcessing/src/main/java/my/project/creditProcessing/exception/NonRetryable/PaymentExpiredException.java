package my.project.creditProcessing.exception.NonRetryable;

import my.project.creditProcessing.exception.NonRetryableException;

public class PaymentExpiredException extends NonRetryableException {
    public PaymentExpiredException(String message) {
        super(message);
    }
}
