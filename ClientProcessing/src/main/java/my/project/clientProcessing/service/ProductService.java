package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.ProductCreateDto;
import my.project.clientProcessing.dto.ProductResponseDto;
import my.project.clientProcessing.dto.ProductUpdateDto;
import my.project.clientProcessing.entity.product.ProductKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductResponseDto> findAllProducts(Pageable pageable);
    Page<ProductResponseDto> findAllProductsByKey(Pageable pageable, ProductKey productKey);
    ProductResponseDto findProductById(Long id);
    ProductResponseDto createProduct(ProductCreateDto productCreateDto);
    ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto);
    void deleteProduct(Long id);

}
