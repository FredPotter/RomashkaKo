package ru.romashka.model.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import ru.romashka.model.entity.Product;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> dataStore;

    public InMemoryProductRepository() {
        this.dataStore = new ArrayList<>();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(dataStore);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return dataStore.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        dataStore.add(product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        dataStore.removeIf(product -> product.getId().equals(id));
    }
}

