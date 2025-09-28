package my.project.accountProcessing.util;

import lombok.RequiredArgsConstructor;
import my.lib.core.*;
import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.CardCreateDto;
import my.project.accountProcessing.dto.PaymentDto;
import my.project.accountProcessing.dto.TransactionDto;
import my.project.accountProcessing.mapper.AccountMapper;
import my.project.accountProcessing.mapper.CardMapper;
import my.project.accountProcessing.mapper.PaymentMapper;
import my.project.accountProcessing.mapper.TransactionMapper;
import my.project.accountProcessing.service.AccountService;
import my.project.accountProcessing.service.CardService;
import my.project.accountProcessing.service.PaymentService;
import my.project.accountProcessing.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumers {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final CardService cardService;
    private final CardMapper cardMapper;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    // принимаем запросы на создание/обновление account
    @KafkaListener(topics = "client_products")
    public void handle(ClientProductAccountEvent clientProductAccountEvent) {

        // общих полей у двух сервисов не так уж и много - clientId, productId и status
        // буду считать, что если статус = opened - то это запрос на создание аккаунта
        if (clientProductAccountEvent.getStatus() == StatusEnum.OPENED) {
            AccountCreateDto accountCreateDto = accountMapper.toDtoFromEvent(clientProductAccountEvent);
            accountService.createAccount(accountCreateDto);
        }
        // ToDo обновление аккаунта (пока нет в задании)
    }

    // запросы на создание карты
    @KafkaListener(topics = "client_cards")
    public void handle(CardCreateEvent cardCreateEvent) {
        CardCreateDto cardCreateDto = cardMapper.toDtoFromEvent(cardCreateEvent);
        cardService.createCard(cardCreateDto);
    }

    // запросы по транзакциям
    @KafkaListener(topics = "client_transactions")
    public void handle(TransactionEvent transactionEvent) {
        TransactionDto transactionDto = transactionMapper.toDtoFromEvent(transactionEvent);
        transactionService.processTransaction(transactionDto);
    }

    // запросы по платежам
    @KafkaListener(topics = "client_payments")
    public void handle(PaymentEvent paymentEvent) {
        PaymentDto paymentDto = paymentMapper.toDtoFromEvent(paymentEvent);
        paymentService.processPayment(paymentDto);
    }

}
