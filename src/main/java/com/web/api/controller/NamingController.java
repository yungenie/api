package com.web.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.api.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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

    @PostMapping("/lai")
    public MemberResponse getResToAPI(@RequestBody MemberRequest req) {
        log.info("Request v1/issue/naming/lombok POST/ getValue");

        // 외부 API 응답 객체
        String s = "{\"data\" : {\"aBCDNo\":\"hello world\", \"AAaa\":\"please\"}}";
        System.out.println("s = " + s);

        // 응답 JSON 문자열 -> JSON 객체 변환
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObjectData = (JsonObject) jsonParser.parse(s.toString());
        System.out.println("jsonObjectData = " + jsonObjectData);

        // JSON 객체 -> JSON 문자열 변환
        Gson gson = new Gson();
        String dataJsonStr = gson.toJson(jsonObjectData.get("data"));
        System.out.println("dataJsonStr = " + dataJsonStr);

        // JSON 문자열 Map 담기
        MemberResponse resVO = new MemberResponse();
        Map<String, Object> map = new HashMap<>();
        map.put("resultVal", dataJsonStr);
        System.out.println("map = " + map);

        // Map에서 JSON 문자열 꺼내서 자사 NLP 엔진(서비스)에 Response 객체 전달
        resVO = gson.fromJson((String) map.get("resultVal"), MemberResponse.class);
        System.out.println("resVO = " + resVO);

        return resVO;
    }

}
