package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.CardCreateEvent;
import my.lib.core.StatusEnum;
import my.project.clientProcessing.dto.CardCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.mapper.CardMapper;
import my.project.clientProcessing.service.CardService;
import my.project.clientProcessing.service.ClientProductService;
import my.project.clientProcessing.util.KafkaProducers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final ClientProductService clientProductService;
    private final KafkaProducers kafkaProducers;
    private final CardMapper cardMapper;

    @Override
    public void createCard(Long accountId, CardCreateDto cardCreateDto) {

        CardCreateEvent cardCreateEvent = new CardCreateEvent();
        cardCreateEvent.setAccountId(accountId);
        cardCreateEvent.setPaymentSystem(cardCreateDto.paymentSystem());

        kafkaProducers.kafkaSendCard(cardCreateEvent);
    }

}
