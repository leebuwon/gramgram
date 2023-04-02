package com.ll.gramgram.domain.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdmHoneController {

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showMain() {
        return "adm/home/main";
    }
}
