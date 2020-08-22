package ru.otus.feedmysail.repository.custom.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.feedmysail.model.ProductResult;
import ru.otus.feedmysail.repository.custom.UserProductRepositoryCustom;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UserProductRepositoryCustomImpl implements UserProductRepositoryCustom {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProductRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductResult> getProductAvgByTeamId(long teamId) {
        Map<String, Object> params = Collections.singletonMap("id", teamId);
        //Таблица category в этом запросе только за ради "ООП"
        List<ProductResult> productResults = jdbcTemplate.query(
                "select up.product_id as productId, p.name as productName, p.category_id as categoryId, k.name as categoryName, AVG(up.vote) as result , MIN(up.vote) as min from user_team ut, user_product up, product p, category k where p.id=up.product_id and k.id=p.category_id and up.user_id=ut.user_id and ut.team_id=:id group by up.product_id, p.name, up.product_id, p.category_id, k.name",
                params,
                new ProductResultMapper());
        return productResults;
    }

}
