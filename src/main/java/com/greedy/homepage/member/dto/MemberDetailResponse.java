package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.Department;
import com.greedy.homepage.project.domain.ProjectMember;

import java.util.List;

public record MemberDetailResponse(
        Long id,
        String name,
        String githubUrl,
        String imageUrl,
        List<Department> departments,
        List<MemberActionResponse> memberActions,
        String description,
        List<TeamProjectResponse> teamProjects
) {
    public static MemberDetailResponse of(Member member, List<MemberAction> memberActions, List<ProjectMember> projectMembers) {
        return new MemberDetailResponse(
                member.getId(),
                member.getName(),
                member.getGithubUrl(),
                member.getImageUrl(),
                member.getDepartments(),
                memberActions.stream().map(MemberActionResponse::from).toList(),
                member.getDescription(),
                projectMembers.stream().map(TeamProjectResponse::from).toList()
        );
    }
}
