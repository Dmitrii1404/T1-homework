package my.lib.core;

import java.math.BigDecimal;

public class ClientProductCreditEvent {


    private Long clientId;
    private Long productId;
    private Integer monthCount;
    private BigDecimal creditAmount;

    public ClientProductCreditEvent() {}

    public ClientProductCreditEvent(Long clientId, Long productId, Integer monthCount, BigDecimal creditAmount) {
        this.clientId = clientId;
        this.productId = productId;
        this.monthCount = monthCount;
        this.creditAmount = creditAmount;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }
}
