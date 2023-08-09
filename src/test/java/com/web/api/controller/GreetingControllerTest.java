package com.web.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void greeting() throws Exception {
        mockMvc.perform(get("/greeting")) //MockMvcRequestBuilders
                .andDo(print()) //MockMvcResultHandlers http를 통해서 컨트롤러랑 데이터를 주고 받는 모든 정보를 로그에 뿌려준다.
                .andExpect(status().isOk()) //MockMvcResultMatchers 상태코드 체크
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    void greetingWithName() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "윤진"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, 윤진!"));
    }
}