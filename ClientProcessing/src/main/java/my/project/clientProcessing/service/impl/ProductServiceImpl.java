package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ProductCreateDto;
import my.project.clientProcessing.dto.ProductResponseDto;
import my.project.clientProcessing.dto.ProductUpdateDto;
import my.project.clientProcessing.entity.product.Product;
import my.project.clientProcessing.entity.product.ProductKey;
import my.project.clientProcessing.exception.NotFoundException;
import my.project.clientProcessing.mapper.ProductMapper;
import my.project.clientProcessing.repository.ProductRepository;
import my.project.clientProcessing.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllProductsByKey(Pageable pageable, ProductKey productKey) {
        Page<Product> products = productRepository.findAllProductsByKey(pageable, productKey);
        return products.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + id)
        ));
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductCreateDto productCreateDto) {
        Product product = productRepository.save(productMapper.toEntity(productCreateDto));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Не найден продукт с указанным ID: " + id)
        );

        productMapper.updateFromDto(productUpdateDto, product);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
