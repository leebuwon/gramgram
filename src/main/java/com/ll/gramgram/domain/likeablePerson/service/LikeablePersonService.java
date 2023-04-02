package com.ll.gramgram.domain.likeablePerson.service;

import com.ll.gramgram.domain.instaMember.entity.InstaMember;
import com.ll.gramgram.domain.instaMember.service.InstaMemberService;
import com.ll.gramgram.domain.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.domain.likeablePerson.repository.LikeablePersonRepository;
import com.ll.gramgram.domain.member.entitiy.Member;
import com.ll.gramgram.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.attoparser.dom.INestableNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeablePersonService {

    private final LikeablePersonRepository likeablePersonRepository;
    private final InstaMemberService instaMemberService;

    public RsData<LikeablePerson> create(Member member, String username, int attractiveTypeCode) {
        InstaMember toInstaMember = instaMemberService.findByUsername(username).orElseThrow();

        if (member.getInstaMember().getUsername().equals(username)){
            RsData.of("F-1", "본인을 호감상대로 등록할 수 없습니다.");
        }

        LikeablePerson likeablePerson = LikeablePerson.builder()
                .fromInstaMember(member.getInstaMember())
                .fromInstaMemberUsername(member.getInstaMember().getUsername())
                .toInstaMember(toInstaMember)
                .toInstaMemberUsername(toInstaMember.getUsername())
                .attractiveTypeCode(attractiveTypeCode)
                .build();

        likeablePersonRepository.save(likeablePerson);

        return RsData.of("S-1", "입력하신 인스타유저(%s)를 호감상대로 등록되었습니다.".formatted(username), likeablePerson);
    }
}
