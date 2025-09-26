package my.lib.core;

public class CardCreateEvent {

    private Long accountId;
    private PaymentSystem paymentSystem;

    public CardCreateEvent() {
    }

    public CardCreateEvent(Long accountId, PaymentSystem paymentSystem) {
        this.accountId = accountId;
        this.paymentSystem = paymentSystem;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
