package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.feedmysail.controller.rest.dto.ProductDto;
import ru.otus.feedmysail.controller.rest.dto.ProductResultDto;
import ru.otus.feedmysail.controller.rest.dto.UserProductDto;
import ru.otus.feedmysail.model.Product;
import ru.otus.feedmysail.model.UserProduct;
import ru.otus.feedmysail.service.ProductService;
import ru.otus.feedmysail.service.UserProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductRestController {

    private final ProductService productService;
    private final UserProductService userProductService;

    public ProductRestController(ProductService productService, UserProductService userProductService) {
        this.productService = productService;
        this.userProductService = userProductService;
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.findAll().stream().map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/product")
    public void insertProduct(
            Product product
    ) {
        productService.save(product);
    }

    @GetMapping("/user/{userId}/products")
    public List<UserProductDto> getUserProducts(@PathVariable("userId") long userId) {
        return userProductService.findByUserId(userId).stream().map(UserProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/user/{userId}/product")
    public void voteProduct(
            @PathVariable("userId") long userId,
            long productId,
            Integer vote
    ) {
        userProductService.save(new UserProduct(0, userId, productId, vote));
    }

    @GetMapping("/teamProductResult/{teamId}")
    public List<ProductResultDto> getTeamProductResult(@PathVariable("teamId") long teamId) {
        return userProductService.getProductAvgByTeamId(teamId).stream().map(ProductResultDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/teamFilteredProductResult/{teamId}")
    public List<ProductResultDto> getFilteredProductResult(@PathVariable("teamId") long teamId) {
        return userProductService.getFilteredProductAvgByTeamId(teamId, 2).stream().map(ProductResultDto::toDto)
                .collect(Collectors.toList());
    }
}
