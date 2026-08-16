package com.greedy.homepage.support.fixture;

import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectImage;
import org.springframework.test.util.ReflectionTestUtils;

public class ProjectImageFixtureBuilder {

    private final Project project;
    private String imageUrl = "https://example.com/project-image.png";

    private ProjectImageFixtureBuilder(Project project) {
        this.project = project;
    }

    public static ProjectImageFixtureBuilder withProject(Project project) {
        return new ProjectImageFixtureBuilder(project);
    }

    public ProjectImageFixtureBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProjectImage build() {
        return ProjectImage.builder()
                .project(project)
                .imageUrl(imageUrl)
                .build();
    }

    public ProjectImage buildWithId(Long id) {
        ProjectImage projectImage = build();
        ReflectionTestUtils.setField(projectImage, "id", id);
        return projectImage;
    }
}
