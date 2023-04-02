package com.ll.gramgram.domain.likeablePerson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/likeablePerson")
public class LikeablePersonController {

    @GetMapping("/add")
    public String showAdd() {
        return "usr/likeablePerson/add";
    }
}
