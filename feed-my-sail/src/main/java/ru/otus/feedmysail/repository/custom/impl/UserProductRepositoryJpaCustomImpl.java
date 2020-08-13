package ru.otus.feedmysail.repository.custom.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.feedmysail.model.ProductResult;
import ru.otus.feedmysail.repository.custom.UserProductRepositoryJpaCustom;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UserProductRepositoryJpaCustomImpl implements UserProductRepositoryJpaCustom {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProductRepositoryJpaCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductResult> getProductAvgByTeamId(long teamId) {
        Map<String, Object> params = Collections.singletonMap("id", teamId);
        List<ProductResult> productResults = jdbcTemplate.query(
                "select up.product_id as productId, p.name as productName, p.category_id as categoryId, AVG(up.vote) as result , MIN(up.vote) as min from user_team ut, user_product up, product p where p.id=up.product_id and up.user_id=ut.user_id and ut.team_id=:id group by up.product_id",
                params,
                new ProductResultMapper());
        return productResults;
    }

}
