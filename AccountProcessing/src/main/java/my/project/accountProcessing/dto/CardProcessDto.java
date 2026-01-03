package my.project.accountProcessing.dto;

import my.lib.core.TransactionType;
import my.project.accountProcessing.entity.account.Account;

import java.math.BigDecimal;

public record CardProcessDto(

        Account account,
        TransactionType type,
        BigDecimal amount

) {}
