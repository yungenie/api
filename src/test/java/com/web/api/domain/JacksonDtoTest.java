package com.web.api.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class JacksonDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void 잭슨_Getter() throws IOException {
        JacksonDto dto = new JacksonDto();
        dto.setaBCDNo("hello");

        // writeValueAsString(변활할 객체) : Java 객체를 JSON 형식으로 변환
        String s = this.objectMapper.writeValueAsString(dto);

        System.out.println("s = " + s);
    }
}