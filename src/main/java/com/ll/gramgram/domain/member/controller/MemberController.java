package com.ll.gramgram.domain.member.controller;

import com.ll.gramgram.domain.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// gramgram 9강 까지 들었음

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "usr/member/join";
    }

    @Getter
    @AllArgsConstructor
    private static class JoinForm{
        @NotBlank
        @Size(min = 4, max =  30)
        private final String username;

        @NotBlank
        @Size(min = 4, max =  30)
        private final String password;
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
//        String username = joinForm.getUsername();
//        String password = joinForm.getPassword();
        memberService.join(joinForm.getUsername(), joinForm.getPassword());;
        return "redirect:/";
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "usr/member/login";
    }
}
