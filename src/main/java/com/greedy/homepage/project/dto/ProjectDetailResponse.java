package com.greedy.homepage.project.dto;

import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectImage;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.domain.enums.ProjectType;
import java.util.List;

public record ProjectDetailResponse(
        Long id,
        String name,
        String summary,
        String thumbnailUrl,
        Integer generationNumber,
        String purpose,
        String mainFunction,
        ProjectType projectType,
        String siteUrl,
        String backendGithubUrl,
        String frontendGithubUrl,
        List<String> backendStack,
        List<String> frontendStack,
        List<String> screenshotUrls,
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
