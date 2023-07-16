package com.web.api.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


class NamingDtoTest {

    public static void main(String[] args) throws IOException {

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
        NamingDto resVO = new NamingDto();
        Map<String, Object> map = new HashMap<>();
        map.put("resultVal", dataJsonStr);
        System.out.println("map = " + map);

        // Map에서 JSON 문자열 꺼내서 자사 NLP 엔진(서비스)에 Response 객체 전달
        resVO = gson.fromJson((String) map.get("resultVal"), NamingDto.class);
        System.out.println("resVO = " + resVO);

    }

    private ObjectMapper objectMapper;

    @BeforeEach // 테스트 메서드 실행 이전에 수행됨
    public void setUp(){
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void 롬북사용() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDto result = this.objectMapper.readValue(json, NamingDto.class);
        System.out.println("result = " + result);
        //assertThat(result.getABCDNo(), is("hello world"));
    }

    @Test
    public void Getter_Setter_직접생성() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDtoV2 result = this.objectMapper.readValue(json, NamingDtoV2.class);
        assertThat(result.getaBCDNo(), is("hello world"));
    }

    @Test
    public void 롬복_잭슨애노테이션적용() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDtoV3 result = this.objectMapper.readValue(json, NamingDtoV3.class);
        assertThat(result.getABCDNo(), is("hello world"));
    }

    @Test
    public void JsonString_to_JsonObject변환() throws JsonProcessingException {
        // JsonString <-> Object 변환 방법
        // 입력이 Json 형태의 String으로 들어오는 상황
        String s = "{\"aBCDNo\":\"hello world\", \"ACDNo\":\"please\"}";
        ObjectMapper objmapper = new ObjectMapper();
        JsonNode jsonNode = objmapper.readTree(s);
        System.out.println("jsonNode = " + jsonNode);
    }

}