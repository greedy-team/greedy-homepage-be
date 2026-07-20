package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.ExternalMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalMemberRepository extends JpaRepository<ExternalMember, Long> {
}
