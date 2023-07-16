package com.web.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Getter/Setter 직접생성
 * Getter 네이밍 규칙
 * - 변수명 그대로 유지
 */
@ToString
@NoArgsConstructor
public class NamingDtoV2 {

    //@Schema(name = "aBCDNo")
    private String aBCDNo;

    //@Schema(name = "AAaa")
    private String AAaa;

    private String BBBb;
    private String CCcC;
    private String DDDD;
    private String AAAAAAa;
    private String Aa;

    //@Schema(name = "aA")
    private String aA;

    private String aaA;
    private String Fab;


    public String getaBCDNo() {
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

    public String getBBBb() {
        return BBBb;
    }

    public void setBBBb(String BBBb) {
        this.BBBb = BBBb;
    }

    public String getCCcC() {
        return CCcC;
    }

    public void setCCcC(String CCcC) {
        this.CCcC = CCcC;
    }

    public String getDDDD() {
        return DDDD;
    }

    public void setDDDD(String DDDD) {
        this.DDDD = DDDD;
    }

    public String getAAAAAAa() {
        return AAAAAAa;
    }

    public void setAAAAAAa(String AAAAAAa) {
        this.AAAAAAa = AAAAAAa;
    }

    public String getAa() {
        return Aa;
    }

    public void setAa(String aa) {
        Aa = aa;
    }

    public String getaA() {
        return aA;
    }

    public void setaA(String aA) {
        this.aA = aA;
    }

    public String getAaA() {
        return aaA;
    }

    public void setAaA(String aaA) {
        this.aaA = aaA;
    }

    public String getFab() {
        return Fab;
    }

    public void setFab(String fab) {
        Fab = fab;
    }
}
