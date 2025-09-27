package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.dto.ClientResponseDto;
import my.project.clientProcessing.dto.UserResponseDto;
import my.project.clientProcessing.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    // POST запрос api/v1/clients/register
    // регистрирует нового клиента + создает User
    /* тело запроса:
    *  {
    *       "region": "00",
    *       "bank": "00",
    *       "firstName": "name",
    *       "middleName"?: "name",
    *       "lastName"?: "name",
    *       "dateOfBirth"?: "yyyy-mm-dd",
    *       "documentType": "PASSPORT | INT_PASSPORT | BIRTH_CERT",
    *       "documentId": "1234567890",
    *       "documentPrefix"?: "1234567890",
    *       "documentSuffix"?: "1234567890",
    *       "login": "login",
    *       "email": "email@some.any",
    *       "password": "8-symbol-min"
    *   }
    * возвращает:
    *   status: 201
    *   {
    *       "id": "user_id",
    *       "login": "login",
    *       "email": "email@some.any"
    *   }
    * */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody ClientCreateDto clientCreateDto) {
        return ResponseEntity.status(201).body(clientService.clientRegistry(clientCreateDto));
    }

    // ToDo что если поля некотрые пустые в БД?
    // GET запрос api/v1/clients/{id}
    /* возвращает:
    *   status: 200
    *   {
    *       "firstName": "name",
    *       "middleName": "name",
    *       "lastName": "name",
    *       "documentId": "1234567890"
    *   }
    * */
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }
}
