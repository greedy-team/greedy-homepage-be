package com.greedy.homepage.support.fixture;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import org.springframework.test.util.ReflectionTestUtils;

public class ActivityImageFixtureBuilder {

    private final Activity activity;
    private String imageUrl = "https://example.com/activity-image.png";

    private ActivityImageFixtureBuilder(Activity activity) {
        this.activity = activity;
    }

    public static ActivityImageFixtureBuilder withActivity(Activity activity) {
        return new ActivityImageFixtureBuilder(activity);
    }

    public ActivityImageFixtureBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ActivityImage build() {
        return ActivityImage.builder()
                .activity(activity)
                .imageUrl(imageUrl)
                .build();
    }

    public ActivityImage buildWithId(Long id) {
        ActivityImage activityImage = build();
        ReflectionTestUtils.setField(activityImage, "id", id);
        return activityImage;
    }
}
