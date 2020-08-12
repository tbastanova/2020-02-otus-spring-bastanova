package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.model.UserProduct;
import ru.otus.feedmysail.repository.UserProductRepositoryJpa;
import ru.otus.feedmysail.service.UserProductService;

import java.util.List;
import java.util.Optional;

@Service
public class UserProductServiceImpl implements UserProductService {
    private final UserProductRepositoryJpa userProductRepositoryJpa;

    public UserProductServiceImpl(UserProductRepositoryJpa userProductRepositoryJpa) {
        this.userProductRepositoryJpa = userProductRepositoryJpa;
    }

    @Override
    public List<UserProduct> findAll() {
        return userProductRepositoryJpa.findAll();
    }

    @Override
    public List<UserProduct> findByUserId(long userId) {
        return userProductRepositoryJpa.findByUserId(userId);
    }

    @Override
    public Optional<UserProduct> findByUserIdAndProductId(long userId, long productId) {
        return userProductRepositoryJpa.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public UserProduct save(UserProduct userProduct) {
        UserProduct userProductRepo = findByUserIdAndProductId(userProduct.getUserId(), userProduct.getProductId()).orElse(userProduct);
        userProductRepo.setVote(userProduct.getVote());
        return userProductRepositoryJpa.save(userProductRepo);
    }
}
