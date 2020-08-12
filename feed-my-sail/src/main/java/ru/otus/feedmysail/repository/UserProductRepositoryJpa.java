package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.UserProduct;

import java.util.List;
import java.util.Optional;

public interface UserProductRepositoryJpa extends CrudRepository<UserProduct, Long> {

    List<UserProduct> findAll();

    List<UserProduct> findByUserId(long userId);

    Optional<UserProduct> findByUserIdAndProductId(long userId, long productId);

}
