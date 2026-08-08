package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.BaseMember;
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
    public static MemberListResponse of(BaseMember member, List<MemberAction> memberActions) {
        List<Department> departments = member instanceof Member m ? m.getDepartments() : List.of();

        return new MemberListResponse(
                member.getId(),
                member.getName(),
                member.getGithubUrl(),
                departments,
                memberActions.stream().map(MemberActionResponse::from).toList()
        );
    }
}
