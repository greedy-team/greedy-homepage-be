package com.greedy.homepage.project.dto;

import com.greedy.homepage.project.domain.Project;

public record ProjectListResponse(
        Long id,
        String name,
        String summary,
        String thumbnailUrl,
        Integer generationNumber
) {
    public static ProjectListResponse from(Project project) {
        return new ProjectListResponse(
                project.getId(),
                project.getName(),
                project.getSummary(),
                project.getThumbnailUrl(),
                project.getGeneration().getNumber()
        );
    }
}
