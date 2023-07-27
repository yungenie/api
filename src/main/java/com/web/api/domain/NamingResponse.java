package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Lombok + Jackson 애노테이션 추가
 */


@Setter(AccessLevel.PRIVATE)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NamingResponse {

    @JsonProperty(value = "aBCDNo")
    private String aBCDNo;

    @JsonProperty(value = "AAaa")
    private String AAaa;

    public void updateAAaa(String AAaa) {
        this.AAaa = AAaa;
    }
}
