package my.project.clientProcessing.util;

import lombok.AllArgsConstructor;
import my.lib.core.CardCreateEvent;
import my.lib.core.ClientProductAccountEvent;
import my.lib.core.ClientProductCreditEvent;
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

    private KafkaTemplate<String, Object> kafkaTemplate;

    // отправка сообщения в топик client_products
    public void kafkaSendClientProductAccount(ProductKey productKey, ClientProductAccountEvent clientProductAccountEvent) {

        // проверка, что ключ продукта подходит
        switch (productKey) {
            case DC, CC, NS, PENS:
                kafkaTemplate.send(Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-products")), clientProductAccountEvent.getClientId().toString(), clientProductAccountEvent);
                break;
            default:
                throw new NotFoundException("Указанный ключ продукта не подходит к данной категории. Ключ: " + productKey);
        }
    }

    // отправка сообщения в топик client_credit_products
    public void kafkaSendClientProductCredit(ProductKey productKey, ClientProductCreditEvent clientProductCreditEvent) {

        // проверка, что ключ продукта подходит
        switch (productKey) {
            case IPO, PC, AC:
                kafkaTemplate.send(Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-credit-products")), clientProductCreditEvent.getClientId().toString(), clientProductCreditEvent);
                break;
            default:
                throw new NotFoundException("Указанный ключ продукта не подходит к данной категории. Ключ: " + productKey);
        }
    }

    // отправка сообщения в топик client_cards
    public void kafkaSendCard(CardCreateEvent cardCreateEvent) {
        kafkaTemplate.send(Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-cards")), cardCreateEvent.getAccountId().toString(), cardCreateEvent);
    }

}
