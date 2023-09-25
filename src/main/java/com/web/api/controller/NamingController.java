package com.web.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.api.domain.*;
import com.web.api.service.NamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * getter/setter jackson naming convention 테스트
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/issue/naming")
public class NamingController {

    private final NamingService namingService;

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
    public ResponseEntity<NamingDtoV3> getValueV4(@RequestBody NamingDtoV3 dto) {
        log.info("Request /v1/issue/naming/javabeans-Jackson-dto POST/ getValueV4");
        log.info(String.valueOf(dto));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/lombok-Jackson-api")
    public ResponseEntity<NamingResponse> getValueV4(@RequestBody NamingRequest dto) {
        log.info("Request /v1/issue/naming/javabeans-Jackson-dto POST/ getValueV4");
        log.info(String.valueOf(dto));

        NamingResponse res = new NamingResponse(dto.getABCDNo(), dto.getAAaa());
        res.updateAAaa(dto.getAAaa() + "수정메서드 확인");
        log.info(String.valueOf(res));

        return ResponseEntity.ok(res);
    }

    @PostMapping("/link-api")
    public ResponseEntity<NamingApiResponse> getResToAPI(@RequestBody NamingApiRequest req) {
        log.info("Request v1/issue/naming/link-api POST/ getResToAPI");

        NamingApiResponse res = namingService.getApiResponse();

        return  ResponseEntity.ok(res);
    }

    /**
     * getter/setter 이슈 최종 정리용
     */
    @GetMapping("/getApiResponse")
    public ResponseEntity<NamingApiResponse2> getValueV5() {
        // 외부 API 응답 문자열
        String s = "{\"data\" : {\"requestId\":\"1234253\",\"aCSGNo\":\"91\", \"AAaa\":\"test\",\"prcssResult\":\"ok\"}}";
        log.info("s = " + s);

        // 문자열 -> JSON객체 변환
        JsonObject jsonObjectData = (JsonObject) JsonParser.parseString(s.toString());
        log.info("jsonObjectData = " + jsonObjectData);

        // JSON객체 -> JSON문자열 변환
        Gson gson = new Gson();
        String dataJsonStr = gson.toJson(jsonObjectData.get("data"));
        log.info("dataJsonStr = " + dataJsonStr);

        // JSON문자열 -> HashMap 담기
        Map<String, Object> map = new HashMap<>();
        map.put("resultVal", dataJsonStr);
        log.info("map = " + map);

        // HashMap에서 JSON문자열와 응답 도메인 객체 매핑
        NamingApiResponse2 resVO = gson.fromJson((String) map.get("resultVal"), (Type) NamingApiResponse2.class);
        log.info("resVO = " + resVO);

        resVO.updateAAaa("update value");

        // 내부엔진에 Response 객체 전달
        return ResponseEntity.ok(resVO);
    }

}
