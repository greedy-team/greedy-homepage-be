package com.greedy.homepage.project.controller;

import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import com.greedy.homepage.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController implements ProjectControllerDocs {

    private final ProjectService projectService;

    @Override
    @GetMapping
    public ResponseEntity<Map<String, List<ProjectListResponse>>> findAll() {
        List<ProjectListResponse> projects = projectService.findAll();
        return ResponseEntity.ok(Map.of("items", projects));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }
}
