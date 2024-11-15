package ru.romashka.model.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;

    @NotNull(message = "Название товара обязательно")
    @Size(max = 255, message = "Название товара не может превышать 255 символов")
    private String name;

    @Size(max = 4096, message = "Описание товара не может превышать 4096 символов")
    private String description;

    @Min(value = 0, message = "Цена товара не может быть меньше 0")
    private Long priceKopecks = 0L;

    private Boolean available = Boolean.FALSE;

    public Product(String name, String description, Long priceKopecks) {
        this.name = name;
        this.description = description;
        this.priceKopecks = priceKopecks != null ? priceKopecks : 0L;
        this.available = Boolean.FALSE;
    }
}
