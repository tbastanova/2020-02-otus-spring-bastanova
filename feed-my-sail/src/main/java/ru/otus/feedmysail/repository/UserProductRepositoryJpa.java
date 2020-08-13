package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.UserProduct;
import ru.otus.feedmysail.repository.custom.UserProductRepositoryJpaCustom;

import java.util.List;
import java.util.Optional;

public interface UserProductRepositoryJpa extends CrudRepository<UserProduct, Long>, UserProductRepositoryJpaCustom {

    List<UserProduct> findAll();

    List<UserProduct> findByUserId(long userId);

    Optional<UserProduct> findByUserIdAndProductId(long userId, long productId);

}
