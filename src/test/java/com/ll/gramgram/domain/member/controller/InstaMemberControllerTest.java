package com.ll.gramgram.domain.member.controller;

import com.ll.gramgram.domain.instaMember.controller.InstaMemberController;
import com.ll.gramgram.domain.instaMember.entity.InstaMember;
import com.ll.gramgram.domain.instaMember.service.InstaMemberService;
import com.ll.gramgram.domain.member.entitiy.Member;
import com.ll.gramgram.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class InstaMemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private InstaMemberService instaMemberService;

    @Test
    @DisplayName("인스타회원 정보 입력 폼")
    @WithUserDetails("user1")
    void t001() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/instaMember/connect"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(InstaMemberController.class))
                .andExpect(handler().methodName("showConnect"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        <input type="text" name="username"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="radio" name="gender" value="W"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="radio" name="gender" value="M"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="submit" value="정보입력"
                        """.stripIndent().trim())));
    }

    @Test
    @DisplayName("로그인을 안하고 인스타회원 정보 입력 페이지에 접근하면 로그인 페이지로 302")
    void t002() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/instaMember/connect"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(InstaMemberController.class))
                .andExpect(handler().methodName("showConnect"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/member/login**"));
    }

    @Test
//    @Rollback(value = false)
    @DisplayName("인스타 회원 정보 입력 폼 처리")
    @WithUserDetails("user1")
    void t003() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/instaMember/connect")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", "abc123")
                        .param("gender", "W")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(InstaMemberController.class))
                .andExpect(handler().methodName("connect"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/pop**"));

        InstaMember instaMember = instaMemberService.findByUsername("abc123").orElse(null);

        Member member = memberService.findByUsername("user1").orElseThrow();


        assertThat(member.getInstaMember()).isEqualTo(instaMember);
    }
}