package my.lib.core;

public class ClientProductAccountEvent {

    private Long clientId;
    private Long productId;
    private StatusEnum status;
    private boolean isRecalc;

    public ClientProductAccountEvent() {}

    public ClientProductAccountEvent(Long clientId, Long productId, StatusEnum status, boolean isRecalc) {
        this.clientId = clientId;
        this.productId = productId;
        this.status = status;
        this.isRecalc = isRecalc;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    public void setIsRecalc(boolean isRecalc) {
        this.isRecalc = isRecalc;
    }

    public Long getClientId() {
        return clientId;
    }
    public Long getProductId() {
        return productId;
    }
    public StatusEnum getStatus() {
        return status;
    }
    public boolean getIsRecalc() {
        return isRecalc;
    }
}
