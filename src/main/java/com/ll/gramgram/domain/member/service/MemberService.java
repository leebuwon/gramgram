package com.ll.gramgram.domain.member.service;

import com.ll.gramgram.domain.member.entitiy.Member;
import com.ll.gramgram.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 아래 메서드들이 전부 readeOnly로 작동한다.
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Optional<Member> findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public Member join(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        return memberRepository.save(member);
    }
}
