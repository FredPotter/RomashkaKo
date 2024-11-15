package ru.romashka.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import ru.romashka.model.entity.Product;
import ru.romashka.rest.dto.ProductDTO;
import ru.romashka.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(product -> objectMapper.convertValue(product, ProductDTO.class)) // Преобразование сущности в DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return objectMapper.convertValue(product, ProductDTO.class);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Product product = objectMapper.convertValue(productDTO, Product.class);
        Product createdProduct = productService.createProduct(product);
        ProductDTO createdProductDTO = objectMapper.convertValue(createdProduct, ProductDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @Valid @RequestBody ProductDTO updatedProductDTO,
                                                    BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Product updatedProduct = objectMapper.convertValue(updatedProductDTO, Product.class);
        Product product = productService.editProduct(id, updatedProduct);
        ProductDTO updatedProductResponseDTO = objectMapper.convertValue(product, ProductDTO.class);

        return ResponseEntity.ok(updatedProductResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindingException(BindException exception, Locale locale) {
        List<String> errorMessages = exception.getAllErrors().stream()
                .map(error -> messageSource.getMessage(error, locale))
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, messageSource.getMessage("validation.error.title", null, locale));
        problemDetail.setProperty("errors", errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

}
