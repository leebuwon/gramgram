package com.ll.gramgram.domain.instaMember.service;

import com.ll.gramgram.domain.instaMember.entity.InstaMember;
import com.ll.gramgram.domain.instaMember.repository.InstaMemberRepository;
import com.ll.gramgram.domain.member.entitiy.Member;
import com.ll.gramgram.domain.member.service.MemberService;
import com.ll.gramgram.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstaMemberService {
    private final InstaMemberRepository instaMemberRepository;
    private final MemberService memberService;

    public Optional<InstaMember> findByUsername(String username) {
        return instaMemberRepository.findByUsername(username);
    }

    @Transactional
    public RsData<InstaMember> connect(Member member, String username, String gender) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 인스타그램 아이디는 이미 다른 사용자와 연결되었습니다.");
        }

        RsData<InstaMember> instaMemberRsData = create(username, gender);

        memberService.updateInstaMember(member, instaMemberRsData.getData());

        return instaMemberRsData;
    }

    private RsData<InstaMember> create(String username, String gender) {
        InstaMember instaMember = InstaMember.builder()
                .username(username)
                .gender(gender)
                .build();

        instaMemberRepository.save(instaMember);

        return RsData.of("S-1", "인스타계정이 등록되었습니다.", instaMember);
    }
}
