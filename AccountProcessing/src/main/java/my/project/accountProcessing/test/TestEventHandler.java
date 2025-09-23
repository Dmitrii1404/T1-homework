package my.project.accountProcessing.test;

import my.lib.core.ClientProductEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventHandler {

    @KafkaListener(topics = "client_products")
    public void handle(ClientProductEvent clientProductEvent) {
        System.out.println(clientProductEvent.getClientId());
        System.out.println(clientProductEvent.getProductId());
        System.out.println(clientProductEvent.getStatus());
    }
}
