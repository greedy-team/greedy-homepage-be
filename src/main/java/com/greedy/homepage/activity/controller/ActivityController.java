package com.greedy.homepage.activity.controller;

import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController implements ActivityControllerDocs {

    private final ActivityService activityService;

    @Override
    @GetMapping
    public ResponseEntity<Map<String, List<ActivityListResponse>>> findAll() {
        List<ActivityListResponse> activities = activityService.findAll();
        return ResponseEntity.ok(Map.of("items", activities));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }
}
