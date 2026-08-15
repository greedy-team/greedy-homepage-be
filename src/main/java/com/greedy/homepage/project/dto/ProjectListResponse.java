package com.greedy.homepage.project.dto;

import com.greedy.homepage.project.domain.Project;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProjectListResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long id,

        @Schema(description = "프로젝트명", example = "그리디 홈페이지")
        String name,

        @Schema(description = "프로젝트 요약", nullable = true)
        String summary,

        @Schema(description = "썸네일 URL", nullable = true)
        String thumbnailUrl,

        @Schema(description = "기수 번호", example = "1")
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
