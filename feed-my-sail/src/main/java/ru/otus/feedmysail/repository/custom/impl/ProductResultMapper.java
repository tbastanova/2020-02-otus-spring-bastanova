package ru.otus.feedmysail.repository.custom.impl;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.feedmysail.model.ProductResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResultMapper implements RowMapper<ProductResult> {
    public ProductResult mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("productId");
        long result = resultSet.getLong("result");
        long min = resultSet.getLong("min");
        ProductResult productResult = new ProductResult(id, result, min, (min > 0 ? result : 0));
        return productResult;
    }
}