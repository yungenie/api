package com.web.api.domain;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ItemResponse {

    private Long itemId;
    private String itemName;
    private Integer itemPrice;

    @Builder
    public ItemResponse(Long itemId, String itemName, Integer itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
