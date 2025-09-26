package my.project.clientProcessing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ProductCreateDto;
import my.project.clientProcessing.dto.ProductResponseDto;
import my.project.clientProcessing.dto.ProductUpdateDto;
import my.project.clientProcessing.entity.product.ProductKey;
import my.project.clientProcessing.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProducts(pageable));
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<Page<ProductResponseDto>> getAllProductsByKey(@PathVariable("key") ProductKey key, Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProductsByKey(pageable, key));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        return ResponseEntity.ok(productService.createProduct(productCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @Valid @RequestBody ProductUpdateDto productUpdateDto
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
