package com.greedy.homepage.member.controller;

import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import com.greedy.homepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @Override
    @GetMapping
    public ResponseEntity<Map<String, List<MemberListResponse>>> findAll() {
        List<MemberListResponse> members = memberService.findAll();
        return ResponseEntity.ok(Map.of("items", members));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }
}
