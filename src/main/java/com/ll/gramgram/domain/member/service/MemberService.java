package com.ll.gramgram.domain.member.service;

import com.ll.gramgram.domain.instaMember.entity.InstaMember;
import com.ll.gramgram.domain.member.entitiy.Member;
import com.ll.gramgram.domain.member.repository.MemberRepository;
import com.ll.gramgram.global.rsData.RsData;
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

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public RsData<Member> join(String username, String password) {
        // 이런 유효성 테스트는 컨트롤러에서 해도되고 서비스에서 해도된다.
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 아이디(%s)는 이미 사용 중 입니다.".formatted(username));
        }


        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);

        return RsData.of("S-1", "회원가입 완료되었습니다.", member);
    }

    @Transactional
    public void updateInstaMember(Member member, InstaMember instaMember) {
        member.setInstaMember(instaMember);
        memberRepository.save(member);
    }
}
