package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.TransactionEvent;
import my.project.clientProcessing.dto.TransactionCreateDto;
import my.project.clientProcessing.service.TransactionService;
import my.project.clientProcessing.util.KafkaProducers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final KafkaProducers kafkaProducers;

    // выполнение транзакции - отправка сообщения в kafka
    @Override
    public void createTransaction(Long cardId, TransactionCreateDto transactionCreateDto) {

        // собираю event
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setCardId(cardId);
        transactionEvent.setType(transactionCreateDto.type());
        transactionEvent.setAmount(transactionCreateDto.amount());

        // отправляю в kafka
        kafkaProducers.kafkaSendTransactionEvent(transactionEvent);
    }
}
