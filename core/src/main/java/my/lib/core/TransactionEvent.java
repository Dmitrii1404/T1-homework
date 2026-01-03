package my.lib.core;

import java.math.BigDecimal;

public class TransactionEvent {

    private Long cardId;
    private TransactionType type;
    private BigDecimal amount;

    public TransactionEvent() {}

    public TransactionEvent(TransactionType type, Long cardId, BigDecimal amount) {
        this.type = type;
        this.cardId = cardId;
        this.amount = amount;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
