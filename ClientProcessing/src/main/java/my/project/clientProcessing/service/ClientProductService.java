package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.ClientProductCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.dto.ClientProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientProductService {

    ClientProductResponseDto getClientProductById(Long id);
    Page<ClientProductResponseDto> getAllClientProducts(Pageable pageable);
    ClientProductResponseDto createClientProduct(ClientProductCreateDto clientProductCreateDto);
    ClientProductResponseDto updateClientProduct(Long id, ClientProductUpdateDto clientProductUpdateDto);
    void deleteClientProduct(Long id);

}
