package com.web.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.api.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * getter/setter jackson naming convention 테스트
 */

@Slf4j
@RestController
@RequestMapping("v1/issue/naming")
public class NamingController {

    @PostMapping("/lombok")
    public ResponseEntity<NamingDto> getValue(@RequestBody NamingDto dto) {
        log.info("Request v1/issue/naming/lombok POST/ getValue");
        log.info(String.valueOf(dto));
        return ResponseEntity.ok(dto);
    }

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
        res.updateAAaa(dto.getAAaa() + "수정메서드 확인");
        log.info(String.valueOf(res));
        return res;
    }

    @PostMapping("/link-api")
    public Optional<MemberResponse> getResToAPI(@RequestBody MemberRequest req) {
        log.info("Request v1/issue/naming/link-api POST/ getResToAPI");

        // 외부 API 응답 객체
        String s = "{\"data\" : {\"aBCDNo\":\"hello world\", \"AAaa\":\"please\"}}";
        log.info("s = " + s);

        // 응답 JSON 문자열 -> JSON 객체 변환
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObjectData = (JsonObject) jsonParser.parse(s.toString());
        log.info("jsonObjectData = " + jsonObjectData);

        // JSON 객체 -> JSON 문자열 변환
        Gson gson = new Gson();
        String dataJsonStr = gson.toJson(jsonObjectData.get("data"));
        log.info("dataJsonStr = " + dataJsonStr);

        // JSON 문자열 Map 담기
        Map<String, Object> map = new HashMap<>();
        map.put("resultVal", dataJsonStr);
        log.info("map = " + map);

        // Map에서 JSON 문자열 꺼내 엔진(서비스)에 Response 객체 전달
        Optional<MemberResponse> resVO = Optional.ofNullable(
                gson.fromJson((String) map.get("resultVal"), MemberResponse.class));
        log.info("resVO = " + resVO);

        return resVO;
    }

}
