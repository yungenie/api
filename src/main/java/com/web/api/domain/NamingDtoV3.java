package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Lombok + Jackson 애노테이션 추가
 */

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NamingDtoV3 {

    @JsonProperty(value = "aBCDNo")
    private String aBCDNo;

    @JsonProperty(value = "AAaa")
    private String AAaa;

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
    private String aA;
}
