package com.greedy.homepage.activity.controller;

import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.common.config.swagger.ApiErrorCode;
import com.greedy.homepage.common.exception.FailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "activities", description = "활동 API")
public interface ActivityControllerDocs {

    @Operation(summary = "전체 활동 목록", description = "정렬: startDate 내림차순")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "활동 목록 조회 성공")
    })
    ResponseEntity<Map<String, List<ActivityListResponse>>> findAll();

    @Operation(summary = "활동 상세")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "활동 상세 조회 성공")
    })
    @ApiErrorCode({FailMessage.NOT_FOUND_ACTIVITY})
    ResponseEntity<ActivityDetailResponse> findById(Long id);
}
