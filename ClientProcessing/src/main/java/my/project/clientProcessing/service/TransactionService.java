package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.TransactionCreateDto;

public interface TransactionService {

    void createTransaction(Long cardId, TransactionCreateDto transactionCreateDto);

}
