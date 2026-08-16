package com.greedy.homepage.support.fixture;

import com.greedy.homepage.activity.domain.Activity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class ActivityFixtureBuilder {

    private String name = "스터디";
    private String summary = "스터디 요약";
    private String description = "스터디 상세 설명";
    private LocalDate startDate = LocalDate.of(2025, 3, 1);
    private LocalDate endDate = LocalDate.of(2025, 6, 30);

    public static ActivityFixtureBuilder builder() {
        return new ActivityFixtureBuilder();
    }

    public ActivityFixtureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ActivityFixtureBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public ActivityFixtureBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ActivityFixtureBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ActivityFixtureBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Activity build() {
        return Activity.builder()
                .name(name)
                .summary(summary)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public Activity buildWithId(Long id) {
        Activity activity = build();
        ReflectionTestUtils.setField(activity, "id", id);
        return activity;
    }
}
