package com.greedy.homepage.activity.controller;

import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityImageResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.activity.service.ActivityService;
import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.common.log.CommonLogInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ActivityService activityService;

    @MockitoBean
    private CommonLogInformation commonLogInformation;

    @Nested
    @DisplayName("전체 활동 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("활동이 존재하면 200 상태코드와 목록을 반환한다")
        void success_withExistingActivities() throws Exception {
            // given
            ActivityListResponse response = new ActivityListResponse(
                    1L, "스터디", "스터디 요약",
                    LocalDate.of(2025, 3, 1), LocalDate.of(2025, 6, 30),
                    2, List.of("https://example.com/img1.png", "https://example.com/img2.png")
            );

            given(activityService.findAll()).willReturn(List.of(response));

            // when & then
            mockMvc.perform(get("/activities"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(1))
                    .andExpect(jsonPath("$.items[0].name").value("스터디"))
                    .andExpect(jsonPath("$.items[0].thumbnailUrls.length()").value(2));
        }

        @Test
        @DisplayName("활동이 없으면 200 상태코드와 빈 목록을 반환한다")
        void success_emptyList() throws Exception {
            // given
            given(activityService.findAll()).willReturn(List.of());

            // when & then
            mockMvc.perform(get("/activities"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(0));
        }
    }

    @Nested
    @DisplayName("활동 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 활동 ID로 조회하면 200 상태코드와 상세 정보를 반환한다")
        void success_withExistingActivityId() throws Exception {
            // given
            ActivityImageResponse imageResponse = new ActivityImageResponse(1L, "https://example.com/img1.png");
            ActivityDetailResponse detailResponse = new ActivityDetailResponse(
                    1L, "스터디", "알고리즘 스터디",
                    LocalDate.of(2025, 3, 1), LocalDate.of(2025, 6, 30),
                    List.of(imageResponse)
            );

            given(activityService.findById(1L)).willReturn(detailResponse);

            // when & then
            mockMvc.perform(get("/activities/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("스터디"))
                    .andExpect(jsonPath("$.description").value("알고리즘 스터디"))
                    .andExpect(jsonPath("$.images.length()").value(1))
                    .andExpect(jsonPath("$.images[0].url").value("https://example.com/img1.png"));
        }

        @Test
        @DisplayName("존재하지 않는 활동 ID로 조회하면 404 상태코드를 반환한다")
        void error_notFoundActivity() throws Exception {
            // given
            given(activityService.findById(999L))
                    .willThrow(new HomepageException(FailMessage.NOT_FOUND_ACTIVITY));

            // when & then
            mockMvc.perform(get("/activities/{id}", 999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(40403))
                    .andExpect(jsonPath("$.message").value("활동을 찾을 수 없습니다."));
        }

        @Test
        @DisplayName("잘못된 타입의 ID로 조회하면 400 상태코드를 반환한다")
        void error_invalidIdType() throws Exception {
            // when & then
            mockMvc.perform(get("/activities/{id}", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(40003));
        }
    }
}
