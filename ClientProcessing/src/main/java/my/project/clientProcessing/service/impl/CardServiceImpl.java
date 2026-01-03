package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.CardCreateEvent;
import my.project.clientProcessing.dto.CardCreateDto;
import my.project.clientProcessing.service.CardService;
import my.project.clientProcessing.util.KafkaProducers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final KafkaProducers kafkaProducers;

    // создание карты - отправка сообщения в kafka
    @Override
    public void createCard(Long accountId, CardCreateDto cardCreateDto) {

        // создание EventDto
        CardCreateEvent cardCreateEvent = new CardCreateEvent();
        cardCreateEvent.setAccountId(accountId);
        cardCreateEvent.setPaymentSystem(cardCreateDto.paymentSystem());

        // отправка сообщения
        kafkaProducers.kafkaSendCard(cardCreateEvent);
    }

}
