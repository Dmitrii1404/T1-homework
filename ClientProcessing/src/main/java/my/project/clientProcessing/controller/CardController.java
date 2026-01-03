package my.project.clientProcessing.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.CardCreateDto;
import my.project.clientProcessing.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cards")
public class CardController {

    private final CardService cardService;

    // POST запрос api/v1/cards/{accountId}
    // создает карту для указанного аккаунта
    /* тело запроса:
    *   {
    *       "paymentSystem": "VISA | MASTERCARD | MIR"
    *   }
    * возвращает:
    *   status: 201
    *   тело: none
    * */
    @PostMapping("/{accountId}")
    public ResponseEntity<Void> createCard(
            @PathVariable("accountId") Long accountId,
            @Valid @RequestBody CardCreateDto cardCreateDto
    ) {
        cardService.createCard(accountId, cardCreateDto);
        return ResponseEntity.status(201).build();
    }

}
