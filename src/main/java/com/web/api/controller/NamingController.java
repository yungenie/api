package com.web.api.controller;

import com.web.api.domain.*;
import com.web.api.service.NamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
