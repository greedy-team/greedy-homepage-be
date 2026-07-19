package com.greedy.homepage.externalmember.repository;

import com.greedy.homepage.externalmember.domain.ExternalMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalMemberRepository extends JpaRepository<ExternalMember, Long> {
}
