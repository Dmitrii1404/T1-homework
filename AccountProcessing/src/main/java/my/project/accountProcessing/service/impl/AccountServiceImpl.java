package my.project.accountProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.AccountUpdateDto;
import my.project.accountProcessing.entity.account.Account;
import my.project.accountProcessing.exception.NonRetryable.NotFountException;
import my.project.accountProcessing.mapper.AccountMapper;
import my.project.accountProcessing.repository.AccountRepository;
import my.project.accountProcessing.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Value("${properties.interest-rate}")
    private BigDecimal interestRate;

    // создание account
    @Override
    @Transactional
    public void createAccount(AccountCreateDto accountCreateDto) {
        Account account = accountMapper.toEntity(accountCreateDto);
        account.setInterestRate(interestRate);
        accountRepository.save(account);
    }

    // обновление account
    @Override
    @Transactional
    public void updateAccount(AccountUpdateDto accountUpdateDto) {

        // поиск account, который надо обновить
        Account account = accountRepository.findById(accountUpdateDto.getAccountId()).orElseThrow(
                () -> new NotFountException("Аккаунт с указанным Id не найден")
        );

        // обновление полей
        accountMapper.updateFromDto(accountUpdateDto, account);
        accountRepository.save(account);
    }

}
