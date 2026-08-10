package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.ProjectMember;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeamProjectResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트명", example = "그리디 홈페이지")
        String name,

        @Schema(description = "기술 스택 포지션", example = "BACKEND")
        StackPosition stackPosition
) {
    public static TeamProjectResponse from(ProjectMember projectMember) {
        return new TeamProjectResponse(
                projectMember.getProject().getId(),
                projectMember.getProject().getName(),
                projectMember.getStackPosition()
        );
    }
}
