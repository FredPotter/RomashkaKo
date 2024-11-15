package ru.romashka.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romashka.model.entity.Product;
import ru.romashka.model.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(NoSuchElementException::new);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            productRepository.deleteById(id);
        }
    }

    public Product editProduct(Long id, Product product) throws NoSuchElementException {
        return productRepository.findById(id)
                .map(prod -> {
                    prod.setName(product.getName());
                    prod.setDescription(product.getDescription());
                    prod.setPriceKopecks(product.getPriceKopecks());
                    prod.setAvailable(product.getAvailable());
                    return prod;
                })
                .orElseThrow(NoSuchElementException::new);
    }
}
