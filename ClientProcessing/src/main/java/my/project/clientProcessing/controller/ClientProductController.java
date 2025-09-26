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
@RequestMapping("api/v1/client-product")
public class ClientProductController {

    private final ClientProductService clientProductService;

    @GetMapping
    public ResponseEntity<Page<ClientProductResponseDto>> getAllClientProducts(Pageable pageable) {
        return ResponseEntity.ok(clientProductService.getAllClientProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProductResponseDto> getClientProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientProductService.getClientProductById(id));
    }

    @PostMapping("/account")
    public ResponseEntity<ClientProductResponseDto> createClientProduct(@Valid @RequestBody ClientProductAccountCreateDto clientProductAccountCreateDto) {
        return ResponseEntity.ok(clientProductService.createClientProductAccount(clientProductAccountCreateDto));
    }

    @PostMapping("/credit")
    public ResponseEntity<ClientProductResponseDto> createClientProduct(@Valid @RequestBody ClientProductCreditCreateDto clientProductCreditCreateDto) {
        return ResponseEntity.ok(clientProductService.createClientProductCredit(clientProductCreditCreateDto));
    }

}
