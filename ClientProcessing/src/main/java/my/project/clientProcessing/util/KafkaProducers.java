package my.project.clientProcessing.util;

import lombok.AllArgsConstructor;
import my.lib.core.*;
import my.project.clientProcessing.entity.product.ProductKey;
import my.project.clientProcessing.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;


@Component
@AllArgsConstructor
public class KafkaProducers {

    @Autowired
    Environment environment;

    private KafkaTemplate<String, Object> kafkaTemplate;

    // отправка сообщения в топик client_products
    public void kafkaSendClientProductAccount(
            ProductKey productKey,
            ClientProductAccountEvent clientProductAccountEvent
    ) {
        // проверка, что ключ продукта подходит
        switch (productKey) {
            case DC, CC, NS, PENS:
                kafkaTemplate.send(
                        Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-products")),
                        clientProductAccountEvent.getClientId().toString(),
                        clientProductAccountEvent
                );
                break;
            default:
                throw new NotFoundException("Указанный ключ продукта не подходит к данной категории. Ключ: " + productKey);
        }
    }

    // отправка сообщения в топик client_credit_products
    public void kafkaSendClientProductCredit(
            ProductKey productKey,
            ClientProductCreditEvent clientProductCreditEvent
    ) {
        // проверка, что ключ продукта подходит
        switch (productKey) {
            case IPO, PC, AC:
                kafkaTemplate.send(
                        Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-credit-products")),
                        clientProductCreditEvent.getClientId().toString(),
                        clientProductCreditEvent
                );
                break;
            default:
                throw new NotFoundException("Указанный ключ продукта не подходит к данной категории. Ключ: " + productKey);
        }
    }

    // отправка сообщения в топик client_cards
    public void kafkaSendCard(CardCreateEvent cardCreateEvent) {
        kafkaTemplate.send(
                Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-cards")),
                cardCreateEvent.getAccountId().toString(),
                cardCreateEvent
        );
    }

    // отправка сообщения в топик client_transactions
    public void kafkaSendTransactionEvent(TransactionEvent transactionEvent) {
        kafkaTemplate.send(
                Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-transactions")),
                UUID.randomUUID().toString(),
                transactionEvent
        );
    }

    // отправка сообщения в топик client_payments
    public void kafkaSendPaymentEvent(PaymentEvent paymentEvent) {
        kafkaTemplate.send(
                Objects.requireNonNull(environment.getProperty("spring.kafka.topics.client-payments")),
                UUID.randomUUID().toString(),
                paymentEvent
        );
    }

}
