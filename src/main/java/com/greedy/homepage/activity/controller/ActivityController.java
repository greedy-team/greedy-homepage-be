package com.greedy.homepage.activity.controller;

import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.activity.service.ActivityService;
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

@Tag(name = "activities", description = "활동 API")
@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "전체 활동 목록", description = "정렬: startDate 내림차순")
    @GetMapping
    public ResponseEntity<Map<String, List<ActivityListResponse>>> findAll() {
        List<ActivityListResponse> activities = activityService.findAll();
        return ResponseEntity.ok(Map.of("items", activities));
    }

    @Operation(summary = "활동 상세")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }
}
