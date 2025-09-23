package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientProductCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.dto.ClientProductUpdateDto;
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

    @PostMapping
    public ResponseEntity<ClientProductResponseDto> createClientProduct(@Valid @RequestBody ClientProductCreateDto clientProductCreateDto) {
        return ResponseEntity.ok(clientProductService.createClientProduct(clientProductCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientProductResponseDto> updateClientProduct(@PathVariable Long id,
            @Valid @RequestBody ClientProductUpdateDto clientProductUpdateDto) {
        return ResponseEntity.ok(clientProductService.updateClientProduct(id, clientProductUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientProductResponseDto> deleteClientProduct(@PathVariable Long id) {
        clientProductService.deleteClientProduct(id);
        return ResponseEntity.ok().build();
    }


}
