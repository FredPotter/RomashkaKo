package ru.romashka.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 4096)
    private String description;

    @Column(nullable = false)
    private Long priceKopecks = 0L;

    @Column(nullable = false)
    private Boolean available = Boolean.FALSE;

    public Product(String name, String description, Long priceKopecks) {
        this.name = name;
        this.description = description;
        this.priceKopecks = priceKopecks != null ? priceKopecks : 0L;
        this.available = Boolean.FALSE;
    }
}
