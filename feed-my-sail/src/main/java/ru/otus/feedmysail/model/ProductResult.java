package ru.otus.feedmysail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResult {
    private long productId;
    private String productName;
    private long categoryId;
    private long result;
    private long min;
    private long calcResult;
}
