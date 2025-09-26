package my.project.accountProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.AccountUpdateDto;
import my.project.accountProcessing.entity.account.Account;
import my.project.accountProcessing.exception.NonRetryable.NotFountException;
import my.project.accountProcessing.mapper.AccountMapper;
import my.project.accountProcessing.repository.AccountRepository;
import my.project.accountProcessing.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public void createAccount(AccountCreateDto accountCreateDto) {
        Account account = accountMapper.toEntity(accountCreateDto);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void updateAccount(AccountUpdateDto accountUpdateDto) {
        Account account = accountRepository.findById(accountUpdateDto.getAccountId()).orElseThrow(
                () -> new NotFountException("Аккаунт с указанным Id не найден")
        );

        accountMapper.updateFromDto(accountUpdateDto, account);
        accountRepository.save(account);
    }

}
