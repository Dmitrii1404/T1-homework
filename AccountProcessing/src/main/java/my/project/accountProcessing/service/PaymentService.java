package my.project.accountProcessing.service;

import my.project.accountProcessing.dto.PaymentCreateDto;
import my.project.accountProcessing.dto.PaymentDto;
import my.project.accountProcessing.entity.account.Account;

public interface PaymentService {

    void createPayment(Account account, PaymentCreateDto paymentCreateDto);
    Account payedPaymentLastDay(Account account);
    void processPayment(PaymentDto paymentDto);

}
