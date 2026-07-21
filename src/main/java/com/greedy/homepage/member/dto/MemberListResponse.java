package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.Department;

import java.util.List;

public record MemberListResponse(
        Long id,
        String name,
        String githubUrl,
        List<Department> departments,
        List<MemberActionResponse> memberActions
) {
    public static MemberListResponse of(Member member, List<MemberAction> memberActions) {
        return new MemberListResponse(
                member.getId(),
                member.getName(),
                member.getGithubUrl(),
                member.getDepartments(),
                memberActions.stream().map(MemberActionResponse::from).toList()
        );
    }
}
