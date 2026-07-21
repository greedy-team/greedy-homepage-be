package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.ActivityImage;

public record ActivityImageResponse(
        Long id,
        String url
) {
    public static ActivityImageResponse from(ActivityImage activityImage) {
        return new ActivityImageResponse(
                activityImage.getId(),
                activityImage.getImageUrl()
        );
    }
}
