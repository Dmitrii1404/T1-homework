package my.project.accountProcessing.service;

import my.project.accountProcessing.dto.TransactionDto;

public interface TransactionService {

    void processTransaction(TransactionDto transactionDto);

}
