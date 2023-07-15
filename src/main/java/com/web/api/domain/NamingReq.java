package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Lombok + Jackson 애노테이션 추가
 */


@Setter(AccessLevel.PRIVATE)
@Getter
@ToString
public class NamingReq {

    @JsonProperty(value = "aBCDNo") // JSON(문자열) -> 객체
    private String aBCDNo;

    @JsonProperty(value = "AAaa") //객체 -> JSON(문자열)
    private String AAaa;

    /*

    @JsonProperty("BBBb")
    private String BBBb;

    @JsonProperty("CCcC")
    private String CCcC;

    @JsonProperty("DDDD")
    private String DDDD;

    @JsonProperty("AAAAAAa")
    private String AAAAAAa;

    @JsonProperty("Aa")
    private String Aa;

    private String aaA;

    @JsonProperty("Fab")
    private String Fab;

    @JsonProperty("aA")
    private String aA;*/
}
