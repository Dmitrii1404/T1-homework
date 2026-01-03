package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.PaymentCreateDto;
import my.project.clientProcessing.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // POST запрос api/v1/payments/{cardId}
    // создает и выполняет платеж (закрытие долгов) по указанной карте
    /* тело запроса:
    *   {
    *       "amount": "1234567890"
    *   }
    * возвращает:
    *   status: 200
    *   тело: none
    * */
    @PostMapping("/{cardId}")
    public ResponseEntity<Void> createPayment(
            @PathVariable("cardId") Long cardId,
            @Valid @RequestBody PaymentCreateDto paymentCreateDto
    ) {
        paymentService.createPayment(cardId, paymentCreateDto);
        return ResponseEntity.status(200).build();
    }

}
