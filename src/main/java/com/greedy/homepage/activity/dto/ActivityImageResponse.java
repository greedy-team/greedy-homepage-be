package com.greedy.homepage.activity.dto;

import com.greedy.homepage.activity.domain.ActivityImage;
import io.swagger.v3.oas.annotations.media.Schema;

public record ActivityImageResponse(
        @Schema(description = "이미지 ID", example = "1")
        Long id,

        @Schema(description = "이미지 URL", example = "https://example.com/image.png")
        String url
) {
    public static ActivityImageResponse from(ActivityImage activityImage) {
        return new ActivityImageResponse(
                activityImage.getId(),
                activityImage.getImageUrl()
        );
    }
}
