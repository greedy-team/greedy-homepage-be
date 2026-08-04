package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record ActivityListResponse(
        @Schema(description = "활동 ID", example = "1")
        Long id,

        @Schema(description = "활동명", example = "1기 스터디")
        String name,

        @Schema(description = "활동 요약", example = "알고리즘 스터디", nullable = true)
        String summary,

        @Schema(description = "시작일", example = "2025-03-01")
        LocalDate startDate,

        @Schema(description = "종료일", example = "2025-06-30")
        LocalDate endDate,

        @Schema(description = "이미지 수", example = "3")
        int imageCount,

        @Schema(description = "썸네일 URL 목록")
        List<String> thumbnailUrls
) {
    public static ActivityListResponse of(Activity activity, List<ActivityImage> images) {
        return new ActivityListResponse(
                activity.getId(),
                activity.getName(),
                activity.getSummary(),
                activity.getStartDate(),
                activity.getEndDate(),
                images.size(),
                images.stream().map(ActivityImage::getImageUrl).toList()
        );
    }
}
