package ru.otus.feedmysail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.feedmysail.model.UserProduct;
import ru.otus.feedmysail.repository.custom.UserProductRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends JpaRepository<UserProduct, Long>, UserProductRepositoryCustom {
    List<UserProduct> findByUserId(long userId);

    Optional<UserProduct> findByUserIdAndProductId(long userId, long productId);
}
