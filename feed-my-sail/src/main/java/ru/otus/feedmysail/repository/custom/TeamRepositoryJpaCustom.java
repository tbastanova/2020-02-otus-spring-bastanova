package ru.otus.feedmysail.repository.custom;

import ru.otus.feedmysail.model.ProductResult;

import java.util.List;

public interface TeamRepositoryJpaCustom {
    List<ProductResult> getProductAvgByTeamId(long teamId);

//    long count();
}
