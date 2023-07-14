package com.web.api.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


class NamingDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach // 테스트 메서드 실행 이전에 수행됨
    public void setUp(){
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void 롬북사용() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDto result = this.objectMapper.readValue(json, NamingDto.class);
        assertThat(result.getABCDNo(), is("hello world"));
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

        //
        //JsonParser jsonParser = new JsonParser();
        //JsonObject jsonObjectData = (JsonObject) jsonParser.parse(sb.toString());
        // -> json-simple 라이브러리 필요함

    }


}