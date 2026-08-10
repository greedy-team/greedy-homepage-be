package com.greedy.homepage.activity.service;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.activity.repository.ActivityImageRepository;
import com.greedy.homepage.activity.repository.ActivityRepository;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.support.fixture.ActivityFixtureBuilder;
import com.greedy.homepage.support.fixture.ActivityImageFixtureBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ActivityServiceUnitTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityImageRepository activityImageRepository;

    @Nested
    @DisplayName("전체 활동 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("활동이 존재하면 startDate 내림차순으로 목록을 반환한다")
        void success_withExistingActivities() {
            // given
            Activity activity1 = ActivityFixtureBuilder.builder()
                    .name("스터디")
                    .startDate(LocalDate.of(2025, 3, 1))
                    .buildWithId(1L);
            Activity activity2 = ActivityFixtureBuilder.builder()
                    .name("해커톤")
                    .startDate(LocalDate.of(2025, 6, 1))
                    .buildWithId(2L);

            ActivityImage image1 = ActivityImageFixtureBuilder.withActivity(activity1)
                    .imageUrl("https://example.com/img1.png")
                    .buildWithId(1L);

            given(activityRepository.findAllByOrderByStartDateDesc())
                    .willReturn(List.of(activity2, activity1));
            given(activityImageRepository.findAllByActivityIdIn(List.of(2L, 1L)))
                    .willReturn(List.of(image1));

            // when
            List<ActivityListResponse> result = activityService.findAll();

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(2);
                softly.assertThat(result.get(0).name()).isEqualTo("해커톤");
                softly.assertThat(result.get(0).thumbnailUrls()).isEmpty();
                softly.assertThat(result.get(1).name()).isEqualTo("스터디");
                softly.assertThat(result.get(1).thumbnailUrls()).hasSize(1);
            });
        }

        @Test
        @DisplayName("이미지가 4개 이상이면 최대 3개만 썸네일로 반환한다")
        void success_limitThumbnailsToThree() {
            // given
            Activity activity = ActivityFixtureBuilder.builder()
                    .name("스터디")
                    .buildWithId(1L);

            List<ActivityImage> images = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> ActivityImageFixtureBuilder.withActivity(activity)
                            .imageUrl("https://example.com/img" + i + ".png")
                            .buildWithId((long) i))
                    .toList();

            given(activityRepository.findAllByOrderByStartDateDesc())
                    .willReturn(List.of(activity));
            given(activityImageRepository.findAllByActivityIdIn(List.of(1L)))
                    .willReturn(images);

            // when
            List<ActivityListResponse> result = activityService.findAll();

            // then
            assertThat(result.get(0).thumbnailUrls()).hasSize(3);
        }

        @Test
        @DisplayName("활동이 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // given
            given(activityRepository.findAllByOrderByStartDateDesc())
                    .willReturn(List.of());

            // when
            List<ActivityListResponse> result = activityService.findAll();

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("활동 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 활동 ID로 조회하면 상세 정보를 반환한다")
        void success_withExistingActivityId() {
            // given
            Activity activity = ActivityFixtureBuilder.builder()
                    .name("스터디")
                    .description("알고리즘 스터디")
                    .buildWithId(1L);

            ActivityImage image = ActivityImageFixtureBuilder.withActivity(activity)
                    .buildWithId(1L);

            given(activityRepository.findById(1L)).willReturn(Optional.of(activity));
            given(activityImageRepository.findAllByActivityId(1L)).willReturn(List.of(image));

            // when
            ActivityDetailResponse result = activityService.findById(1L);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.name()).isEqualTo("스터디");
                softly.assertThat(result.description()).isEqualTo("알고리즘 스터디");
                softly.assertThat(result.images()).hasSize(1);
            });
        }

        @Test
        @DisplayName("존재하지 않는 활동 ID로 조회하면 예외가 발생한다")
        void error_notFoundActivity() {
            // given
            given(activityRepository.findById(999L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> activityService.findById(999L))
                    .isInstanceOf(HomepageException.class)
                    .hasMessage("활동을 찾을 수 없습니다.");
        }
    }
}
