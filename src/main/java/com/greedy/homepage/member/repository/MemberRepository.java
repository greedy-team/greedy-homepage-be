package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
