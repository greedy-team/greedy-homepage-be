package com.greedy.homepage.member.repository;

import com.greedy.homepage.member.domain.MemberAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberActionRepository extends JpaRepository<MemberAction, Long> {

    List<MemberAction> findAllByMemberId(Long memberId);

    List<MemberAction> findAllByMemberIdIn(List<Long> memberIds);

    List<MemberAction> findAllByExternalMemberId(Long externalMemberId);

    List<MemberAction> findAllByExternalMemberIdIn(List<Long> externalMemberIds);
}
