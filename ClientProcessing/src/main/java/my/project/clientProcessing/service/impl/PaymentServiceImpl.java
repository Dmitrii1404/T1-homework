package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.PaymentEvent;
import my.project.clientProcessing.dto.PaymentCreateDto;
import my.project.clientProcessing.service.PaymentService;
import my.project.clientProcessing.util.KafkaProducers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final KafkaProducers kafkaProducers;

    // выполенение платежа - отправка в kafka
    @Override
    public void createPayment(Long cardId, PaymentCreateDto paymentCreateDto) {

        // собираю event
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setCardId(cardId);
        paymentEvent.setAmount(paymentCreateDto.amount());

        // отправляю в kafka
        kafkaProducers.kafkaSendPaymentEvent(paymentEvent);
    }

}
