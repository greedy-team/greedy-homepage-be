package com.greedy.homepage.project.service;

import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import com.greedy.homepage.project.repository.ProjectMemberRepository;
import com.greedy.homepage.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public List<ProjectListResponse> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectListResponse::from)
                .toList();
    }

    public ProjectDetailResponse findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new HomepageException(FailMessage.NOT_FOUND_PROJECT));

        List<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(id);

        return ProjectDetailResponse.of(project, projectMembers);
    }
}
