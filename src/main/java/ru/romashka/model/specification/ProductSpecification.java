package ru.romashka.model.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.romashka.model.entity.Product;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

    public static Specification<Product> createSpecification(String name, Long minPrice, Long maxPrice, Boolean available) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (name != null) {
                predicate = builder.and(predicate, builder.like(root.get("name"), "%" + name + "%"));
            }

            if (minPrice != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("priceKopecks"), minPrice));
            }

            if (maxPrice != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("priceKopecks"), maxPrice));
            }

            if (available != null) {
                predicate = builder.and(predicate, builder.equal(root.get("available"), available));
            }

            return predicate;
        };
    }
}
