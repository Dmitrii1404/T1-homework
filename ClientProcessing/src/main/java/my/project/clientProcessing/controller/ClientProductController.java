package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientProductAccountCreateDto;
import my.project.clientProcessing.dto.ClientProductCreditCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.service.ClientProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client-products")
public class ClientProductController {

    private final ClientProductService clientProductService;

    // GET запрос api/v1/client-products
    // получить список всех открытых продуктов у всех клиентов
    /* параметры запроса:
    *   page=number
    *   size=number
    *   sort=поле,направление
    * возвращает:
    *   status: 200
    *   все поля из типа Page
    *   content: [
    *       {
    *           "id": "client_product_id",
    *           "clientId": "client_id",
    *           "productId": "product_id",
    *           "openDate": "yyyy-mm-dd",
    *           "closeDate": "yyyy-mm-dd",
    *           "status": "ACTIVE | CLOSED | BLOCKED | ARRESTED"
    *       }
    *   ]
    * */
    @GetMapping
    public ResponseEntity<Page<ClientProductResponseDto>> getAllClientProducts(Pageable pageable) {
        return ResponseEntity.ok(clientProductService.getAllClientProducts(pageable));
    }

    // GET запрос api/v1/client-products/{id}
    // получить информацию по указанному пользователю
    /* возвращает:
    *   status: 200
    *   {
    *       "id": "client_product_id",
    *       "clientId": "client_id",
    *       "productId": "product_id",
    *       "openDate": "yyyy-mm-dd",
    *       "closeDate": "yyyy-mm-dd",
    *       "status": "ACTIVE | CLOSED | BLOCKED | ARRESTED"
    *   }
    * */
    @GetMapping("/{id}")
    public ResponseEntity<ClientProductResponseDto> getClientProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientProductService.getClientProductById(id));
    }

    // POST запрос api/v1/client-products/account
    // создает клиентский продукт, типы продукта: DC, CC, NS, PENS
    /* тело запроса:
    *   {
    *       "clientId": "client_id",
    *       "productId": "product_id"
    *   }
    * возвращает:
    *   status: 201
    *   {
    *       "id": "client_product_id",
    *       "clientId": "client_id",
    *       "productId": "product_id",
    *       "openDate": "yyyy-mm-dd",
    *       "closeDate": "yyyy-mm-dd",
    *       "status": "ACTIVE | CLOSED | BLOCKED | ARRESTED"
    *   }
    * */
    @PostMapping("/account")
    public ResponseEntity<ClientProductResponseDto> createClientProduct(@Valid @RequestBody ClientProductAccountCreateDto clientProductAccountCreateDto) {
        return ResponseEntity.status(201).body(clientProductService.createClientProductAccount(clientProductAccountCreateDto));
    }

    // POST запрос api/v1/client-products/credit
    // создает клиентский продукт, типы продукта: IPO, PC, AC
    /* тело запроса:
    *   {
    *       "clientId": "client_id",
    *       "productId": "product_id",
    *       "monthCount": "1234567890",
    *       "creditAmount": "1234567890"
    *   }
    * возвращает:
    *   status: 201
    *   {
    *       "id": "client_product_id",
    *       "clientId": "client_id",
    *       "productId": "product_id",
    *       "openDate": "yyyy-mm-dd",
    *       "closeDate": "yyyy-mm-dd",
    *       "status": "ACTIVE | CLOSED | BLOCKED | ARRESTED"
    *   }
    * */
    @PostMapping("/credit")
    public ResponseEntity<ClientProductResponseDto> createCreditProduct(@Valid @RequestBody ClientProductCreditCreateDto clientProductCreditCreateDto) {
        return ResponseEntity.status(201).body(clientProductService.createClientProductCredit(clientProductCreditCreateDto));
    }

    // ToDo доделать CRUD и реализовать все в других сервисах
}
