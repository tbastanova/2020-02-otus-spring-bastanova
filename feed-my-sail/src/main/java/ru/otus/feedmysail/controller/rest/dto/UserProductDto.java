package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.feedmysail.model.UserProduct;

@Data
@AllArgsConstructor
public class UserProductDto {
    private long id = -1;
    private long user_id = -1;
    private long product_id = -1;
    private Integer vote;

    public static UserProductDto toDto(UserProduct userProduct) {
        return new UserProductDto(userProduct.getId(), userProduct.getUserId(), userProduct.getProductId()
                , userProduct.getVote()
        );
    }

}
