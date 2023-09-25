package com.web.api.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.api.domain.NamingApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NamingService {

    /**
     * 외부 API 연동 로직 중 테스트 요소 부분만 나열
     */
    public NamingApiResponse getApiResponse(){

        // 외부 API 응답 객체
        String s = "{\"data\" : {\"aBCDNo\":\"hello world\", \"AAaa\":\"please\"}}";
        log.info("s = " + s);

        // 응답 JSON 문자열 -> JSON 객체 변환
        //JsonParser jsonParser = new JsonParser();
        //JsonObject jsonObjectData = (JsonObject) jsonParser.parse(s.toString());
        /*
            Gson 2.8.6 버전 이전에는 JsonParser의 parse() 메소드를 이용하여 파싱하였지만,
            Gson 2.8.6 부터는 JsonParser의 생성자와 parse() 메소드가 deprecated 되었다고 한다.
            대신 JsonParser.parseString() 메소드를 이용하여 Json 문자열을 parsing 하고,
            JsonElement 객체를 리턴하는 방식을 사용하면 된다
         */
        JsonObject jsonObjectData = (JsonObject) JsonParser.parseString(s.toString());
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
        NamingApiResponse resVO = gson.fromJson((String) map.get("resultVal"), (Type) NamingApiResponse.class);
        log.info("resVO = " + resVO);

        return resVO;
    }

}
