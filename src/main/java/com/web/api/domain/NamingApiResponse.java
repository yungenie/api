package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class NamingApiResponse {

    @JsonProperty("aBCDNo")
    private String aBCDNo;

    @JsonProperty("AAaa")
    private String AAaa;

/*    public String getaBCDNo() {
        return aBCDNo;
    }

    public void setaBCDNo(String aBCDNo) {
        this.aBCDNo = aBCDNo;
    }

    public String getAAaa() {
        return AAaa;
    }

    public void setAAaa(String AAaa) {
        this.AAaa = AAaa;
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "aBCDNo='" + aBCDNo + '\'' +
                ", AAaa='" + AAaa + '\'' +
                '}';
    }*/
}
