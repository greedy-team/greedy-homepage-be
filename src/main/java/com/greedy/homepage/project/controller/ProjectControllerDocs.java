package com.greedy.homepage.project.controller;

import com.greedy.homepage.common.config.swagger.ApiErrorCode;
import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "projects", description = "프로젝트 API")
public interface ProjectControllerDocs {

    @Operation(summary = "전체 프로젝트 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 목록 조회 성공")
    })
    ResponseEntity<Map<String, List<ProjectListResponse>>> findAll();

    @Operation(summary = "프로젝트 상세")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 상세 조회 성공")
    })
    @ApiErrorCode({FailMessage.NOT_FOUND_PROJECT})
    ResponseEntity<ProjectDetailResponse> findById(Long id);
}
