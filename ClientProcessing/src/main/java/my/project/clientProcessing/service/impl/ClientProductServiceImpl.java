package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.StatusEnum;
import my.project.clientProcessing.dto.ClientProductCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.dto.ClientProductUpdateDto;
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
    public ClientProductResponseDto createClientProduct(ClientProductCreateDto clientProductCreateDto) {
        Client client = clientRepository.findById(clientProductCreateDto.clientId()).orElseThrow(
                () -> new NotFoundException("Не найден пользователь с указанным ID: " + clientProductCreateDto.clientId())
        );

        Product product = productRepository.findById(clientProductCreateDto.productId()).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + clientProductCreateDto.productId())
        );


        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClient(client);
        clientProduct.setProduct(product);
        clientProduct.setStatus(StatusEnum.ACTIVE);

        kafkaProducers.KafkaSendClientProduct(product.getKey(), clientProductMapper.toEventDto(clientProduct));

        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(savedClientProduct);
    }

    @Override
    @Transactional
    public ClientProductResponseDto updateClientProduct(Long id, ClientProductUpdateDto clientProductUpdateDto) {
        ClientProduct clientProduct = clientProductRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден клиентский продукт с указанным ID: " + id)
        );

        clientProductMapper.updateFromDto(clientProductUpdateDto, clientProduct);

        kafkaProducers.KafkaSendClientProduct(clientProduct.getProduct().getKey(), clientProductMapper.toEventDto(clientProduct));

        clientProductRepository.save(clientProduct);

        return clientProductMapper.toDto(clientProduct);
    }

    @Override
    @Transactional
    public void deleteClientProduct(Long id) {
        ClientProduct clientProduct = clientProductRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден клиентский продукт с указанным ID: " + id)
        );

        clientProduct.setStatus(StatusEnum.CLOSED);
        kafkaProducers.KafkaSendClientProduct(clientProduct.getProduct().getKey(), clientProductMapper.toEventDto(clientProduct));

        clientProductRepository.deleteById(id);
    }
}
