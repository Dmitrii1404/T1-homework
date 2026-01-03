package my.lib.core;

import java.math.BigDecimal;

public class PaymentEvent {

    private Long cardId;
    private BigDecimal amount;

    public PaymentEvent() {}

    public PaymentEvent(Long cardId, BigDecimal amount) {
        this.cardId = cardId;
        this.amount = amount;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
