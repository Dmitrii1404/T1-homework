package my.project.accountProcessing.test;

import my.lib.core.DtoTestEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventHandler {

    @KafkaListener(topics = "client_products")
    public void handle(DtoTestEvent dto) {
        System.out.println(dto.getEvent());
    }
}
