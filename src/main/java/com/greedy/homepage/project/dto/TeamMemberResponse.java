package com.greedy.homepage.project.dto;

import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.ProjectMember;

public record TeamMemberResponse(
        Long memberId,
        String name,
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
