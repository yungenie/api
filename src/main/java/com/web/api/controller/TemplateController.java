package com.web.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    @GetMapping("main")
    //@Operation(summary = "템플릿 화면", description = "템플릿 화면을 출력합니다.", tags = {"View"})
    public String selectTemplateView(Model model) {
        model.addAttribute("title", "템플릿 화면");
        return "temp/templatePage";
    }
}
