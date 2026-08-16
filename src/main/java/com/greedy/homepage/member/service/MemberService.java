package com.greedy.homepage.member.service;

import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.member.domain.BaseMember;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import com.greedy.homepage.member.repository.BaseMemberRepository;
import com.greedy.homepage.member.repository.MemberActionRepository;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BaseMemberRepository baseMemberRepository;
    private final MemberActionRepository memberActionRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public List<MemberListResponse> findAll() {
        List<BaseMember> members = baseMemberRepository.findAllOrderByLatestGenerationDesc();
        List<Long> memberIds = members.stream().map(BaseMember::getId).toList();

        Map<Long, List<MemberAction>> actionsByMemberId = memberActionRepository.findAllByMemberIdIn(memberIds)
                .stream()
                .collect(Collectors.groupingBy(action -> action.getMember().getId()));

        return members.stream()
                .map(member -> MemberListResponse.of(
                        member,
                        actionsByMemberId.getOrDefault(member.getId(), List.of())
                ))
                .toList();
    }

    public MemberDetailResponse findById(Long id) {
        BaseMember member = baseMemberRepository.findById(id)
                .orElseThrow(() -> new HomepageException(FailMessage.NOT_FOUND_MEMBER));

        List<MemberAction> memberActions = memberActionRepository.findAllByMemberId(id);
        List<ProjectMember> projectMembers = projectMemberRepository.findAllByMemberId(id);

        return MemberDetailResponse.of(member, memberActions, projectMembers);
    }
}
