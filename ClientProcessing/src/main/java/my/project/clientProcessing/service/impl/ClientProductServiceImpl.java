package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
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

    // получение клиентского продукта по id
    @Override
    @Transactional(readOnly = true)
    public ClientProductResponseDto getClientProductById(Long id) {
        return clientProductMapper.toDto(clientProductRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден клиентский продукт с указанным ID: " + id)
        ));
    }

    // получение всех продуктов у каждого клиента
    @Override
    @Transactional(readOnly = true)
    public Page<ClientProductResponseDto> getAllClientProducts(Pageable pageable) {
        Page<ClientProduct> clientProducts = clientProductRepository.findAll(pageable);
        return clientProducts.map(clientProductMapper::toDto);
    }

    // создание клиентского продукта, обслуживание DC, CC, NS, PENS
    @Override
    @Transactional
    public ClientProductResponseDto createClientProductAccount(ClientProductAccountCreateDto clientProductAccountCreateDto) {

        // создаем сам клиентский продукт
        ClientProduct clientProduct = createClientProduct(
                clientProductAccountCreateDto.clientId(),
                clientProductAccountCreateDto.productId(),
                StatusEnum.ACTIVE
        );

        // отправляем сообщение о создании в kafka (account)
        kafkaProducers.kafkaSendClientProductAccount(
                clientProduct.getProduct().getKey(),
                clientProductMapper.toAccountEventDto(clientProduct)
        );

        // сохраняем клиентский продукт
        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(savedClientProduct);
    }

    // создание клиентского продукта, обслуживание IPO, PC, AC
    @Override
    @Transactional
    public ClientProductResponseDto createClientProductCredit(ClientProductCreditCreateDto clientProductCreditCreateDto) {

        // создаем сам клиентский продукт
        ClientProduct clientProduct = createClientProduct(
                clientProductCreditCreateDto.clientId(),
                clientProductCreditCreateDto.productId(),
                StatusEnum.ACTIVE
        );

        // отправляем сообщение о создании в kafka (credit)
        kafkaProducers.kafkaSendClientProductCredit(
                clientProduct.getProduct().getKey(),
                clientProductMapper.toCreditEventDto(clientProductCreditCreateDto)
        );

        // сохраняем клиентский продукт
        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(savedClientProduct);
    }

    // создание самого клиентского продукта
    private ClientProduct createClientProduct(Long clientId, Long productId, StatusEnum statusEnum) {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new NotFoundException("Не найден пользователь с указанным ID: " + clientId)
        );

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + productId)
        );

        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClient(client);
        clientProduct.setProduct(product);
        clientProduct.setStatus(statusEnum);

        return clientProduct;
    }
}