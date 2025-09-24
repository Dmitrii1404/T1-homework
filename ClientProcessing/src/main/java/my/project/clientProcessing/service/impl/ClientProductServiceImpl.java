package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.ClientProductCreditEvent;
import my.lib.core.StatusEnum;
import my.project.clientProcessing.dto.ClientProductAccountCreateDto;
import my.project.clientProcessing.dto.ClientProductCreditCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.entity.client.Client;
import my.project.clientProcessing.entity.clientProduct.ClientProduct;
import my.project.clientProcessing.entity.product.Product;
import my.project.clientProcessing.exception.NotFoundException;
import my.project.clientProcessing.mapper.ClientProductMapper;
import my.project.clientProcessing.repository.ClientProductRepository;
import my.project.clientProcessing.repository.ClientRepository;
import my.project.clientProcessing.repository.ProductRepository;
import my.project.clientProcessing.service.ClientProductService;
import my.project.clientProcessing.util.KafkaProducers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientProductServiceImpl implements ClientProductService {

    private final ClientProductRepository clientProductRepository;
    private final ClientProductMapper clientProductMapper;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final KafkaProducers kafkaProducers;

    @Override
    @Transactional(readOnly = true)
    public ClientProductResponseDto getClientProductById(Long id) {
        return clientProductMapper.toDto(clientProductRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден клиентский продукт с указанным ID: " + id)
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientProductResponseDto> getAllClientProducts(Pageable pageable) {
        Page<ClientProduct> clientProducts = clientProductRepository.findAll(pageable);
        return clientProducts.map(clientProductMapper::toDto);
    }

    @Override
    @Transactional
    public ClientProductResponseDto createClientProductAccount(ClientProductAccountCreateDto clientProductAccountCreateDto) {
        Client client = clientRepository.findById(clientProductAccountCreateDto.clientId()).orElseThrow(
                () -> new NotFoundException("Не найден пользователь с указанным ID: " + clientProductAccountCreateDto.clientId())
        );

        Product product = productRepository.findById(clientProductAccountCreateDto.productId()).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + clientProductAccountCreateDto.productId())
        );

        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClient(client);
        clientProduct.setProduct(product);
        clientProduct.setStatus(StatusEnum.ACTIVE);

        kafkaProducers.kafkaSendClientProductAccount(product.getKey(), clientProductMapper.toAccountEventDto(clientProduct));

        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(savedClientProduct);
    }

    @Override
    @Transactional
    public ClientProductResponseDto createClientProductCredit(ClientProductCreditCreateDto clientProductCreditCreateDto) {
        Client client = clientRepository.findById(clientProductCreditCreateDto.clientId()).orElseThrow(
                () -> new NotFoundException("Не найден пользователь с указанным ID: " + clientProductCreditCreateDto.clientId())
        );

        Product product = productRepository.findById(clientProductCreditCreateDto.productId()).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + clientProductCreditCreateDto.productId())
        );

        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClient(client);
        clientProduct.setProduct(product);
        clientProduct.setStatus(StatusEnum.ACTIVE);


        kafkaProducers.kafkaSendClientProductCredit(product.getKey(), clientProductMapper.toCreditEventDto(clientProductCreditCreateDto));

        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(savedClientProduct);
    }
}