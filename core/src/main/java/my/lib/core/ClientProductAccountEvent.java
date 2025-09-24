package my.lib.core;

public class ClientProductAccountEvent {

    private Long clientId;
    private Long productId;
    private StatusEnum status;

    public ClientProductAccountEvent() {}

    public ClientProductAccountEvent(Long clientId, Long productId, StatusEnum status) {
        this.clientId = clientId;
        this.productId = productId;
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

    public Long getClientId() {
        return clientId;
    }
    public Long getProductId() {
        return productId;
    }
    public StatusEnum getStatus() {
        return status;
    }
}
