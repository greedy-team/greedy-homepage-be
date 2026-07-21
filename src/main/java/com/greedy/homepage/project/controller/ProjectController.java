package com.greedy.homepage.project.controller;

import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import com.greedy.homepage.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "projects", description = "프로젝트 API")
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "전체 프로젝트 목록")
    @GetMapping
    public ResponseEntity<Map<String, List<ProjectListResponse>>> findAll() {
        List<ProjectListResponse> projects = projectService.findAll();
        return ResponseEntity.ok(Map.of("items", projects));
    }

    @Operation(summary = "프로젝트 상세")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }
}
