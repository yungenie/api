package com.web.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberResponse {

    @Schema(description = "회원 아이디")
    private Integer MemberId;
}
