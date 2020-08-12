package ru.otus.feedmysail.service;

import ru.otus.feedmysail.model.UserProduct;

import java.util.List;
import java.util.Optional;

public interface UserProductService {

    List<UserProduct> findAll();

    List<UserProduct> findByUserId(long userId);

    Optional<UserProduct> findByUserIdAndProductId(long userId, long productId);

    UserProduct save(UserProduct userProduct);

}
