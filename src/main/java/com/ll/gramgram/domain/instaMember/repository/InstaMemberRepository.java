package com.ll.gramgram.domain.instaMember.repository;

import com.ll.gramgram.domain.instaMember.entity.InstaMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstaMemberRepository extends JpaRepository<InstaMember, Long> {
    Optional<InstaMember> findByUsername(String username);

}
