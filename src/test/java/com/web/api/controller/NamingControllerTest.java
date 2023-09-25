package com.web.api.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NamingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("외부 API 응답값을 확인한다.")
    @Test
    void getApiResponse() throws Exception {
        mockMvc.perform(get("/v1/issue/naming/getApiResponse")) //MockMvcRequestBuilders
                .andDo(print()) //MockMvcResultHandlers http를 통해서 컨트롤러랑 데이터를 주고 받는 모든 정보를 로그에 뿌려준다.
                .andExpect(status().isOk()) //MockMvcResultMatchers 상태코드 체크
                .andExpect(jsonPath("$.aCSGNo").value("91")); // 기존필드


    }

}