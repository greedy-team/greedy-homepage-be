package com.greedy.homepage.project.dto;

import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.ProjectMember;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeamMemberResponse(
        @Schema(description = "멤버 ID", example = "1")
        Long memberId,

        @Schema(description = "멤버명", example = "홍길동")
        String name,

        @Schema(description = "기술 스택 포지션", example = "BACKEND")
        StackPosition stackPosition
) {
    public static TeamMemberResponse from(ProjectMember projectMember) {
        return new TeamMemberResponse(
                projectMember.getMember().getId(),
                projectMember.getMember().getName(),
                projectMember.getStackPosition()
        );
    }
}
