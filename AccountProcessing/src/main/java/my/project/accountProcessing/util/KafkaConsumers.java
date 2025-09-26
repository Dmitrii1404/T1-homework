package my.project.accountProcessing.util;

import lombok.RequiredArgsConstructor;
import my.lib.core.CardCreateEvent;
import my.lib.core.ClientProductAccountEvent;
import my.lib.core.StatusEnum;
import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.CardCreateDto;
import my.project.accountProcessing.mapper.AccountMapper;
import my.project.accountProcessing.mapper.CardMapper;
import my.project.accountProcessing.service.AccountService;
import my.project.accountProcessing.service.CardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumers {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final CardService cardService;
    private final CardMapper cardMapper;

    @KafkaListener(topics = "client_products")
    public void handle(ClientProductAccountEvent clientProductAccountEvent) {
        if (clientProductAccountEvent.getStatus() == StatusEnum.ACTIVE) {
            AccountCreateDto accountCreateDto = accountMapper.toDtoFromEvent(clientProductAccountEvent);
            accountService.createAccount(accountCreateDto);
        }
        // ToDo обновление аккаунта (пока нет в задании)
    }

    @KafkaListener(topics = "client_cards")
    public void handle(CardCreateEvent cardCreateEvent) {
        CardCreateDto cardCreateDto = cardMapper.toDtoFromEvent(cardCreateEvent);
        cardService.createCard(cardCreateDto);
    }

    @KafkaListener(topics = "client_transactions")
    public void handle() {
        // ToDo топик client_transactions
    }

}
