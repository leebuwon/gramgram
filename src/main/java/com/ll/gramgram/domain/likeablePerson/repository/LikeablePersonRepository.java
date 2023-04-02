package com.ll.gramgram.domain.likeablePerson.repository;

import com.ll.gramgram.domain.likeablePerson.entity.LikeablePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeablePersonRepository extends JpaRepository<LikeablePerson, Long> {

}
