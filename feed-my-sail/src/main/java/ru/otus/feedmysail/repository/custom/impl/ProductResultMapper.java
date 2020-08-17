package ru.otus.feedmysail.repository.custom.impl;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.feedmysail.model.Category;
import ru.otus.feedmysail.model.Product;
import ru.otus.feedmysail.model.ProductResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResultMapper implements RowMapper<ProductResult> {
    public ProductResult mapRow(ResultSet resultSet, int i) throws SQLException {
        long result = resultSet.getLong("result");
        long min = resultSet.getLong("min");
        ProductResult productResult = new ProductResult(new Product(resultSet.getLong("productId"),
                resultSet.getString("productName"),
                new Category(resultSet.getLong("categoryId"), resultSet.getString("categoryName"))),
                result,
                min,
                (min > 0 ? result : 0));
        return productResult;
    }
}