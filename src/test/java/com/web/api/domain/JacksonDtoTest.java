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

        String s = this.objectMapper.writeValueAsString(dto);

        System.out.println("s = " + s);
    }
}