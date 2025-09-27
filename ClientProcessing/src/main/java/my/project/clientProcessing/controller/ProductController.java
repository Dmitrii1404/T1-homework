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
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    // GET запрос api/v1/products
    // получить список о всех доступных продуктах
    /* параметры запроса:
    *   page=number
    *   size=number
    *   sort=поле,направление
    * возвращает:
    *   status: 200
    *   все поля из типа Page
    *   content: [
    *       {
    *           "id": "product_id",
    *           "name": "name",
    *           "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS",
    *           "productId": "key+id",
    *       }
    *   ]
    * */
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProducts(pageable));
    }

    // GET запрос api/v1/products/key/{key}
    // получить список о всех доступных продуктах с указанным ключем
    /* параметры запроса:
    *   page=number
    *   size=number
    *   sort=поле,направление
    * возвращает:
    *   status: 200
    *   все поля из типа Page
    *   content: [
    *       {
    *           "id": "product_id",
    *           "name": "name",
    *           "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS",
    *           "productId": "key+id",
    *       }
    *   ]
    * */
    @GetMapping("/key/{key}")
    public ResponseEntity<Page<ProductResponseDto>> getAllProductsByKey(@PathVariable("key") ProductKey key, Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProductsByKey(pageable, key));
    }

    // GET запрос api/v1/products/{id}
    // получить продукт по указанному id
    /*
    * возвращает:
    *   status: 200
    *   {
    *       "id": "product_id",
    *       "name": "name",
    *       "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS",
    *       "productId": "key+id",
    *   }
    * */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    // POST запрос api/v1/products
    // создает новый продукт
    /* тело запроса:
    *   {
    *       "name": "name",
    *       "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS"
    *   }
    * возвращает:
    *   status: 201
    *   {
    *       "id": "product_id",
    *       "name": "name",
    *       "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS",
    *       "productId": "key+id"
    *   }
    * */
    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        return ResponseEntity.status(201).body(productService.createProduct(productCreateDto));
    }

    // ToDo у нас же тогда и уже открытые счета обновятся. Типа клиент открыл DC, а потом поменяется на IPO
    // PUT запрос api/v1/products/{id}
    // обновление продукта по указанному id
    /* тело запроса:
    *   {
    *       "name"?: "new_name",
    *       "key"?: "DC | CC | AC | IPO | PC | PENS | NS | INS | BS"
    *   }
    * возвращает:
    *   status: 200
    *   {
    *       "id": "product_id",
    *       "name": "name",
    *       "key": "DC | CC | AC | IPO | PC | PENS | NS | INS | BS",
    *       "productId": "key+id"
    *   }
    * */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @Valid @RequestBody ProductUpdateDto productUpdateDto
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateDto));
    }

    // ToDo опять же - продумать удаление продукта, который уже у кого-то оформлен (и изменение в других сервисах)
    // DELETE запрос api/v1/products/{id}
    // удаляет продукт по указанному id
    /* тело: none
    * возвращает:
    *   status: 204
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(204).build();
    }

}
