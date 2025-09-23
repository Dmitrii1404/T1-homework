package my.project.clientProcessing.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.lib.core.DtoTestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TestService {

    private KafkaTemplate<String, DtoTestEvent> kafkaTemplate;

    public TestService(KafkaTemplate<String, DtoTestEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void testMessage(String message) {

        DtoTestEvent dtoTestEvent = new DtoTestEvent("myTitle", message);

        CompletableFuture<SendResult<String, DtoTestEvent>> d = kafkaTemplate.send("client_products", "111", dtoTestEvent);

        d.whenComplete((r, e) -> {
            System.out.println("Что-то вернул брокер");
        });

        System.out.println(message);
    }

}
