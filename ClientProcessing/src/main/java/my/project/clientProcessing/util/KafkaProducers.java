package my.project.clientProcessing.util;

import lombok.AllArgsConstructor;
import my.lib.core.ClientProductEvent;
import my.lib.core.DtoTestEvent;
import my.project.clientProcessing.entity.product.ProductKey;
import my.project.clientProcessing.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
public class KafkaProducers {

    @Autowired
    Environment environment;

    private KafkaTemplate<String, ClientProductEvent> clientProductKafkaTemplate;

    public void KafkaSendClientProduct(ProductKey productKey, ClientProductEvent clientProductEvent) {
        switch (productKey) {
            case DC, CC, NS, PENS:
                clientProductKafkaTemplate.send(Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-products")), clientProductEvent.getClientId().toString(), clientProductEvent);
                break;
            case IPO, PC, AC:
                clientProductKafkaTemplate.send(Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client_credit_products")), clientProductEvent.getClientId().toString(), clientProductEvent);
                break;
            default:
                throw new NotFoundException("Указанный ключ продукта не может быть обработан. Ключ: " + productKey);
        }
    }

}
