package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.Department;
import com.greedy.homepage.project.domain.ProjectMember;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MemberDetailResponse(
        @Schema(description = "멤버 ID", example = "1")
        Long id,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "GitHub URL", example = "https://github.com/hong", nullable = true)
        String githubUrl,

        @Schema(description = "소속 부서 목록")
        List<Department> departments,

        @Schema(description = "멤버 활동 이력")
        List<MemberActionResponse> memberActions,

        @Schema(description = "멤버 소개", nullable = true)
        String description,

        @Schema(description = "참여 프로젝트 목록")
        List<TeamProjectResponse> teamProjects
) {
    public static MemberDetailResponse of(Member member, List<MemberAction> memberActions, List<ProjectMember> projectMembers) {
        return new MemberDetailResponse(
                member.getId(),
                member.getName(),
                member.getGithubUrl(),
                member.getDepartments(),
                memberActions.stream().map(MemberActionResponse::from).toList(),
                member.getDescription(),
                projectMembers.stream().map(TeamProjectResponse::from).toList()
        );
    }
}
