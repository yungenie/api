package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@ToString
public class NamingApiResponse2 {

    private String requestId; // 세션ID

    @JsonProperty("aCSGNo")
    private String aCSGNo; // 고객분류코드(91,92,93,99)

    @JsonProperty("AAaa")
    private String AAaa; // 테스트 컬럼

    private String prcssResult; // 외부API 응답상태

    public void updateAAaa(String AAaa) {
        this.AAaa = AAaa;
    }

    /*    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getaCSGNo() {
        return aCSGNo;
    }

    public void setaCSGNo(String aCSGNo) {
        this.aCSGNo = aCSGNo;
    }

    public String getAAaa() {
        return AAaa;
    }

    public void setAAaa(String AAaa) {
        this.AAaa = AAaa;
    }

    public String getPrcssResult() {
        return prcssResult;
    }

    public void setPrcssResult(String prcssResult) {
        this.prcssResult = prcssResult;
    }


    @Override
    public String toString() {
        return "MemberResponse{" +
                "aCSGNo='" + aCSGNo + '\'' +
                ", AAaa='" + AAaa + '\'' +
                '}';
    }*/
}
