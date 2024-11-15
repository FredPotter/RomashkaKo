package ru.romashka.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.romashka.model.entity.Product;
import ru.romashka.model.repository.ProductRepository;
import ru.romashka.model.specification.ProductSpecification;
import ru.romashka.rest.dto.ProductDTO;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductDTO> getFilteredAndSortedProducts(String name, Long minPrice, Long maxPrice, Boolean available, int limit, String sortBy, String sortOrder) {
        Specification<Product> spec = ProductSpecification.createSpecification(name, minPrice, maxPrice, available);
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        PageRequest pageRequest = PageRequest.of(0, limit, sort);

        List<Product> products = productRepository.findAll(spec, pageRequest).getContent();
        return products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPriceKopecks(), product.getAvailable()))
                .toList();
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
