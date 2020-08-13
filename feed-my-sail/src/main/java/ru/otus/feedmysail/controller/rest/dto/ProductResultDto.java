package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.feedmysail.model.ProductResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResultDto {
    private long productId = -1;
    private String productName;
    private long categoryId;
    private long result;
    private long min;
    private long calcResult;

    public static ProductResultDto toDto(ProductResult product) {
        return new ProductResultDto(product.getProductId(),
                product.getProductName(),
                product.getCategoryId(),
                product.getResult(),
                product.getMin(),
                product.getCalcResult());
    }

}
