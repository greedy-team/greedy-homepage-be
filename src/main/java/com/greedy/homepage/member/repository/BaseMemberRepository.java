package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.BaseMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseMemberRepository extends JpaRepository<BaseMember, Long> {
}
