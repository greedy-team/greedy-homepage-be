package com.greedy.homepage.support.fixture;

import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectMember;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class ProjectMemberFixtureBuilder {

    private final Project project;
    private final Member member;
    private StackPosition stackPosition = StackPosition.BACKEND;
    private LocalDate startDate = LocalDate.of(2025, 3, 1);
    private LocalDate endDate = LocalDate.of(2025, 8, 31);

    private ProjectMemberFixtureBuilder(Project project, Member member) {
        this.project = project;
        this.member = member;
    }

    public static ProjectMemberFixtureBuilder withProjectAndMember(Project project, Member member) {
        return new ProjectMemberFixtureBuilder(project, member);
    }

    public ProjectMemberFixtureBuilder stackPosition(StackPosition stackPosition) {
        this.stackPosition = stackPosition;
        return this;
    }

    public ProjectMemberFixtureBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ProjectMemberFixtureBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public ProjectMember build() {
        return ProjectMember.builder()
                .project(project)
                .member(member)
                .stackPosition(stackPosition)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public ProjectMember buildWithId(Long id) {
        ProjectMember projectMember = build();
        ReflectionTestUtils.setField(projectMember, "id", id);
        return projectMember;
    }
}
