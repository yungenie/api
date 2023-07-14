package com.web.api.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "상품 요청 도메인")
public class ItemRequest {

    @Schema(description = "상품 아이디")
    private Long itemId;

}
