package my.project.accountProcessing.service;

import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.AccountUpdateDto;

public interface AccountService {

    void createAccount(AccountCreateDto accountCreateDto);
    void updateAccount(AccountUpdateDto accountUpdateDto);

}
