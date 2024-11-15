package ru.romashka.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romashka.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

