package com.greedy.homepage.member.controller;

import com.greedy.homepage.common.config.swagger.ApiErrorCode;
import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "members", description = "멤버 API")
public interface MemberControllerDocs {

    @Operation(summary = "전체 멤버 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 목록 조회 성공")
    })
    ResponseEntity<Map<String, List<MemberListResponse>>> findAll();

    @Operation(summary = "멤버 상세")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 상세 조회 성공")
    })
    @ApiErrorCode({FailMessage.NOT_FOUND_MEMBER})
    ResponseEntity<MemberDetailResponse> findById(Long id);
}
