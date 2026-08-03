package com.greedy.homepage.project.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.project.domain.enums.ProjectType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE project SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String thumbnailUrl;

    @Column
    private String siteUrl;

    @Column
    private String backendGithubUrl;

    @Column
    private String frontendGithubUrl;

    @Column
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String mainFunction;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType projectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", nullable = false)
    private Generation generation;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_backend_stack", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "stack", nullable = false)
    private List<String> backendStack = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_frontend_stack", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "stack", nullable = false)
    private List<String> frontendStack = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectImage> images = new ArrayList<>();

    @Builder
    public Project(
            String name,
            String thumbnailUrl,
            String siteUrl,
            String backendGithubUrl,
            String frontendGithubUrl,
            String summary,
            String mainFunction,
            String purpose,
            ProjectType projectType,
            Generation generation
    ) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.siteUrl = siteUrl;
        this.backendGithubUrl = backendGithubUrl;
        this.frontendGithubUrl = frontendGithubUrl;
        this.summary = summary;
        this.mainFunction = mainFunction;
        this.purpose = purpose;
        this.projectType = projectType;
        this.generation = generation;
    }
}
