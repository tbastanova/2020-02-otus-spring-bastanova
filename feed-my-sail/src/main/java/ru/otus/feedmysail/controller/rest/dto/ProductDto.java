package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.feedmysail.model.Product;

@Data
@AllArgsConstructor
public class ProductDto {
    private long id = -1;
    private String name;

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName());
    }

}
