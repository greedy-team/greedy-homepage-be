package com.greedy.homepage.member.controller;

import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import com.greedy.homepage.member.service.MemberService;
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

@Tag(name = "members", description = "멤버 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "전체 멤버 목록")
    @GetMapping
    public ResponseEntity<Map<String, List<MemberListResponse>>> findAll() {
        List<MemberListResponse> members = memberService.findAll();
        return ResponseEntity.ok(Map.of("items", members));
    }

    @Operation(summary = "멤버 상세")
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }
}
