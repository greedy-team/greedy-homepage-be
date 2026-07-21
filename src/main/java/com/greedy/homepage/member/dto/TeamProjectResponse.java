package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.ProjectMember;

public record TeamProjectResponse(
        Long projectId,
        String name,
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
