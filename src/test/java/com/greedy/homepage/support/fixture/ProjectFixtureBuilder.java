package com.greedy.homepage.support.fixture;

import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.enums.ProjectType;
import org.springframework.test.util.ReflectionTestUtils;

public class ProjectFixtureBuilder {

    private final Generation generation;
    private String name = "테스트 프로젝트";
    private String thumbnailUrl = "https://example.com/thumbnail.png";
    private String summary = "프로젝트 요약";
    private ProjectType projectType = ProjectType.GENERATION;

    private ProjectFixtureBuilder(Generation generation) {
        this.generation = generation;
    }

    public static ProjectFixtureBuilder withGeneration(Generation generation) {
        return new ProjectFixtureBuilder(generation);
    }

    public ProjectFixtureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectFixtureBuilder thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public ProjectFixtureBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public ProjectFixtureBuilder projectType(ProjectType projectType) {
        this.projectType = projectType;
        return this;
    }

    public Project build() {
        return Project.builder()
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .summary(summary)
                .projectType(projectType)
                .generation(generation)
                .build();
    }

    public Project buildWithId(Long id) {
        Project project = build();
        ReflectionTestUtils.setField(project, "id", id);
        return project;
    }
}
