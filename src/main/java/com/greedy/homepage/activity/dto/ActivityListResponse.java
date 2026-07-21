package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;

import java.time.LocalDate;
import java.util.List;

public record ActivityListResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        int imageCount,
        List<String> thumbnailUrls
) {
    public static ActivityListResponse of(Activity activity, List<ActivityImage> images) {
        return new ActivityListResponse(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getStartDate(),
                activity.getEndDate(),
                images.size(),
                images.stream().map(ActivityImage::getImageUrl).toList()
        );
    }
}
