package com.web.api.controller;

import com.web.api.domain.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * note Code Base에 Swagger관련 코드가 많이 들어가게 되는 문제를 해결하기 위해 TDD에 익숙해지면 RestDoc으로 변경하는 것이 좋음.
 * note 컨트롤러 인터페이스를 만들어서 Swagger 관련 내용을 인터페이스에 작성할 수도 있음
 */
@RestController
@RequiredArgsConstructor
public class ItemController {
    @Operation(summary = "상품 조회 요청", description = "상품 정보가 조회됩니다.", tags = { "Item Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/v1/item/{id}")
    ItemResponse getMember(
            @Parameter(description = "회원 ID", required = true, example = "1") @PathVariable("id") Long id) {

        ItemResponse res = ItemResponse.builder()
                .itemId(id)
                .itemName("상품A")
                .itemPrice(10000)
                .build();

        return res;
    }
}
