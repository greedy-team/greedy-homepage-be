package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.BaseMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseMemberRepository extends JpaRepository<BaseMember, Long> {

    @Query("""
            SELECT m FROM BaseMember m
            LEFT JOIN MemberAction ma ON ma.member = m
            LEFT JOIN ma.generation g
            GROUP BY m
            ORDER BY MAX(g.number) DESC NULLS LAST
            """)
    List<BaseMember> findAllOrderByLatestGenerationDesc();
}
