package com.greedy.homepage.project.dto;

import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectImage;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.domain.enums.ProjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ProjectDetailResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long id,

        @Schema(description = "프로젝트명", example = "그리디 홈페이지")
        String name,

        @Schema(description = "프로젝트 요약", nullable = true)
        String summary,

        @Schema(description = "썸네일 URL", nullable = true)
        String thumbnailUrl,

        @Schema(description = "기수 번호", example = "1")
        Integer generationNumber,

        @Schema(description = "프로젝트 목적", nullable = true)
        String purpose,

        @Schema(description = "주요 기능 설명", nullable = true)
        String mainFunction,

        @Schema(description = "프로젝트 유형", example = "TASK_FORCE")
        ProjectType projectType,

        @Schema(description = "서비스 URL", nullable = true)
        String siteUrl,

        @Schema(description = "백엔드 GitHub URL", nullable = true)
        String backendGithubUrl,

        @Schema(description = "프론트엔드 GitHub URL", nullable = true)
        String frontendGithubUrl,

        @Schema(description = "백엔드 기술 스택")
        List<String> backendStack,

        @Schema(description = "프론트엔드 기술 스택")
        List<String> frontendStack,

        @Schema(description = "스크린샷 URL 목록")
        List<String> screenshotUrls,

        @Schema(description = "팀 멤버 목록")
        List<TeamMemberResponse> team
) {
    public static ProjectDetailResponse of(Project project, List<ProjectMember> projectMembers) {
        return new ProjectDetailResponse(
                project.getId(),
                project.getName(),
                project.getSummary(),
                project.getThumbnailUrl(),
                project.getGeneration().getNumber(),
                project.getPurpose(),
                project.getMainFunction(),
                project.getProjectType(),
                project.getSiteUrl(),
                project.getBackendGithubUrl(),
                project.getFrontendGithubUrl(),
                project.getBackendStack(),
                project.getFrontendStack(),
                project.getImages().stream().map(ProjectImage::getImageUrl).toList(),
                projectMembers.stream().map(TeamMemberResponse::from).toList()
        );
    }
}
