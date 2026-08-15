package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record ActivityDetailResponse(
        @Schema(description = "활동 ID", example = "1")
        Long id,

        @Schema(description = "활동명", example = "1기 스터디")
        String name,

        @Schema(description = "활동 상세 설명", nullable = true)
        String description,

        @Schema(description = "시작일", example = "2025-03-01")
        LocalDate startDate,

        @Schema(description = "종료일", example = "2025-06-30")
        LocalDate endDate,

        @Schema(description = "활동 이미지 목록")
        List<ActivityImageResponse> images
) {
    public static ActivityDetailResponse of(Activity activity, List<ActivityImage> images) {
        return new ActivityDetailResponse(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getStartDate(),
                activity.getEndDate(),
                images.stream().map(ActivityImageResponse::from).toList()
        );
    }
}
