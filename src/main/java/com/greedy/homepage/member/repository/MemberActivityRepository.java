package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.MemberActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberActivityRepository extends JpaRepository<MemberActivity, Long> {
}
