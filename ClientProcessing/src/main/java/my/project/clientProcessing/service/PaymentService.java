package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.PaymentCreateDto;

public interface PaymentService {

    void createPayment(Long cardId, PaymentCreateDto paymentCreateDto);
}
