package com.web.api.controller;

import com.web.api.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("v1/issue/naming")
public class NamingController {
    @Operation(summary = "Getter/Setter naming 테스트", description = "테스트 결과를 조회합니다.", tags = { "naming Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = NamingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/lombok")
    public ResponseEntity<NamingDto> getValue(@RequestBody NamingDto dto) {
        log.info("Request v1/issue/naming/lombok POST/ getValue");
        log.info(String.valueOf(dto));
        return ResponseEntity.ok(dto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = NamingDtoV2.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/auto")
    public ResponseEntity<NamingDtoV2> getValueV2(@RequestBody NamingDtoV2 dto) {
        log.info("Request /v1/issue/naming/javabeans POST/ getValueV2");
        log.info(String.valueOf(dto));
        return ResponseEntity.ok(dto);
    }
    @PostMapping("/lombok-Jackson")
    public ResponseEntity<NamingDtoV3> getValueV3(@RequestBody NamingDtoV3 dto) {
        log.info("Request /v1/issue/naming/javabeans-Jackson POST/ getValueV3");
        log.info(String.valueOf(dto));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/lombok-Jackson-dto")
    public NamingDtoV3 getValueV4(@RequestBody NamingDtoV3 dto) {
        log.info("Request /v1/issue/naming/javabeans-Jackson-dto POST/ getValueV4");
        log.info(String.valueOf(dto));
        return dto;
    }

    @GetMapping("/lombok-Jackson-api")
    public NamingRes getValueV4(@RequestBody NamingReq dto) {
        log.info("Request /v1/issue/naming/javabeans-Jackson-dto POST/ getValueV4");
        log.info(String.valueOf(dto));

        NamingRes res = new NamingRes(dto.getABCDNo(), dto.getAAaa());
        res.updateAAaa(dto.getAAaa() + "수정메서드 따로 만들었쥐");
        log.info(String.valueOf(res));
        return res;
    }
}
