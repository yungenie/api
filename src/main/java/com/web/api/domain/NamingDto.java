package com.web.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Lombok 적용
 * Getter 네이밍 규칙
 * - 맨 앞 글자를 대문자로 바꿔서 리턴
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NamingDto {

    private String aBCDNo;
    private String AAaa;
    /*private String BBBb;
    private String CCcC;
    private String DDDD;
    private String AAAAAAa;
    private String Aa;
    private String aaA;
    private String Fab;
    private String aA;*/

}
