package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.TransactionCreateDto;
import my.project.clientProcessing.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // POST запрос api/v1/transactions/{cardId}
    // создает и выполняет транзакцию (пополнение или снятие денег) по указанной карте
    /* тело запроса:
    *   {
    *       "type": "DEPOSIT, WITHDRAW",
    *       "amount": "1234567890"
    *   }
    * возвращает:
    *   status: 200
    *   тело: none
    * */
    @PostMapping("/{cardId}")
    public ResponseEntity<Void> createTransaction(
            @PathVariable("cardId") Long cardId,
            @Valid @RequestBody TransactionCreateDto transactionCreateDto
    ) {
        transactionService.createTransaction(cardId, transactionCreateDto);
        return ResponseEntity.status(200).build();
    }
}
