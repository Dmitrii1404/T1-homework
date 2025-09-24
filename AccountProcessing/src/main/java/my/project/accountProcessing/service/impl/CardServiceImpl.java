package my.project.accountProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.StatusEnum;
import my.project.accountProcessing.dto.AccountUpdateDto;
import my.project.accountProcessing.dto.CardCreateDto;
import my.project.accountProcessing.entity.account.Account;
import my.project.accountProcessing.entity.card.Card;
import my.project.accountProcessing.exception.NonRetryable.AlreadyExistException;
import my.project.accountProcessing.exception.NonRetryable.NotFountException;
import my.project.accountProcessing.exception.NonRetryable.SourceNotActiveException;
import my.project.accountProcessing.mapper.CardMapper;
import my.project.accountProcessing.repository.AccountRepository;
import my.project.accountProcessing.repository.CardRepository;
import my.project.accountProcessing.service.AccountService;
import my.project.accountProcessing.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CardMapper cardMapper;

    @Override
    @Transactional
    public void createCard(CardCreateDto cardCreateDto) {
        Account account = accountRepository.findById(cardCreateDto.accountId()).orElseThrow(
                () -> new NotFountException("Аккаунт с указанным Id не найден")
        );

        if (account.getStatus() != StatusEnum.ACTIVE) {
            throw new SourceNotActiveException("Аккаунт с указанным Id заблокирован");
        }

        if (account.getCardExist()) {
            throw new AlreadyExistException("Карта для данного аккаунта уже существует");
        }


        Card card = cardMapper.toEntity(cardCreateDto);
        card.setAccount(account);
        card.setStatus(StatusEnum.ACTIVE);
        card.setCardId(String.format("%08d", account.getId()));

        AccountUpdateDto accountUpdateDto = AccountUpdateDto.builder()
                .accountId(account.getId())
                .cardExist(true)
                .build();

        accountService.updateAccount(accountUpdateDto);
        cardRepository.save(card);
    }

}
