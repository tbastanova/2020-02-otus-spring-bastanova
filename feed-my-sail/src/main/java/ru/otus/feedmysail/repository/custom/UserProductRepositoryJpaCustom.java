package ru.otus.feedmysail.repository.custom;

import ru.otus.feedmysail.model.ProductResult;

import java.util.List;

public interface UserProductRepositoryJpaCustom {
    List<ProductResult> getProductAvgByTeamId(long teamId);
}
