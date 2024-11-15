package ru.romashka.model.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T entity);
    void deleteById(Long id);
}

