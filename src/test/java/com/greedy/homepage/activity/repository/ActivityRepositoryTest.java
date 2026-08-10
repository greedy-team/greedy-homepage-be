package com.greedy.homepage.activity.repository;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import com.greedy.homepage.support.ServiceIntegrationTest;
import com.greedy.homepage.support.fixture.ActivityFixtureBuilder;
import com.greedy.homepage.support.fixture.ActivityImageFixtureBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ActivityRepositoryTest extends ServiceIntegrationTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityImageRepository activityImageRepository;

    @Nested
    @DisplayName("활동을 startDate 내림차순으로 조회할 때")
    class FindAllByOrderByStartDateDesc {

        @Test
        @DisplayName("startDate 내림차순으로 정렬된 목록을 반환한다")
        void success_orderedByStartDateDesc() {
            // given
            Activity early = activityRepository.save(
                    ActivityFixtureBuilder.builder()
                            .name("스터디")
                            .startDate(LocalDate.of(2025, 1, 1))
                            .build()
            );
            Activity late = activityRepository.save(
                    ActivityFixtureBuilder.builder()
                            .name("해커톤")
                            .startDate(LocalDate.of(2025, 6, 1))
                            .build()
            );

            // when
            List<Activity> result = activityRepository.findAllByOrderByStartDateDesc();

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(2);
                softly.assertThat(result.get(0).getName()).isEqualTo("해커톤");
                softly.assertThat(result.get(1).getName()).isEqualTo("스터디");
            });
        }

        @Test
        @DisplayName("활동이 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // when
            List<Activity> result = activityRepository.findAllByOrderByStartDateDesc();

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("활동 ID로 이미지를 조회할 때")
    class FindAllImagesByActivityId {

        @Test
        @DisplayName("해당 활동의 이미지만 반환한다")
        void success_withExistingActivityId() {
            // given
            Activity activity1 = activityRepository.save(
                    ActivityFixtureBuilder.builder().name("스터디").build()
            );
            Activity activity2 = activityRepository.save(
                    ActivityFixtureBuilder.builder().name("해커톤").build()
            );

            activityImageRepository.save(
                    ActivityImageFixtureBuilder.withActivity(activity1).imageUrl("https://example.com/img1.png").build()
            );
            activityImageRepository.save(
                    ActivityImageFixtureBuilder.withActivity(activity2).imageUrl("https://example.com/img2.png").build()
            );

            // when
            List<ActivityImage> result = activityImageRepository.findAllByActivityId(activity1.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(1);
                softly.assertThat(result.get(0).getImageUrl()).isEqualTo("https://example.com/img1.png");
            });
        }
    }

    @Nested
    @DisplayName("활동 ID 목록으로 이미지를 일괄 조회할 때")
    class FindAllImagesByActivityIdIn {

        @Test
        @DisplayName("해당 활동들의 모든 이미지를 반환한다")
        void success_withMultipleActivityIds() {
            // given
            Activity activity1 = activityRepository.save(
                    ActivityFixtureBuilder.builder().name("스터디").build()
            );
            Activity activity2 = activityRepository.save(
                    ActivityFixtureBuilder.builder().name("해커톤").build()
            );

            activityImageRepository.save(
                    ActivityImageFixtureBuilder.withActivity(activity1).build()
            );
            activityImageRepository.save(
                    ActivityImageFixtureBuilder.withActivity(activity2).build()
            );

            // when
            List<ActivityImage> result = activityImageRepository.findAllByActivityIdIn(
                    List.of(activity1.getId(), activity2.getId())
            );

            // then
            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("빈 ID 목록이면 빈 결과를 반환한다")
        void success_emptyIdList() {
            // when
            List<ActivityImage> result = activityImageRepository.findAllByActivityIdIn(List.of());

            // then
            assertThat(result).isEmpty();
        }
    }
}
