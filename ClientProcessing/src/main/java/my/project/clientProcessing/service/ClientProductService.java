package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.ClientProductAccountCreateDto;
import my.project.clientProcessing.dto.ClientProductCreditCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientProductService {

    ClientProductResponseDto getClientProductById(Long id);
    Page<ClientProductResponseDto> getAllClientProducts(Pageable pageable);
    ClientProductResponseDto createClientProductAccount(ClientProductAccountCreateDto clientProductAccountCreateDto);
    ClientProductResponseDto createClientProductCredit(ClientProductCreditCreateDto clientProductCreditCreateDto);

    // ToDo нужно доделать CRUD - но надо еще продумать логику для него на других сервисах

}
