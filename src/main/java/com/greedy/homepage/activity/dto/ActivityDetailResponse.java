package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;

import java.time.LocalDate;
import java.util.List;

public record ActivityDetailResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
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
