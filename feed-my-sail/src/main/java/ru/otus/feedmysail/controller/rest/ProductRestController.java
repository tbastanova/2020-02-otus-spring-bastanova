package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.feedmysail.controller.rest.dto.ProductDto;
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

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
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
}
