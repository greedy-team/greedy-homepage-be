package com.greedy.homepage.project.controller;

import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.common.log.CommonLogInformation;
import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.project.domain.enums.ProjectType;
import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import com.greedy.homepage.project.dto.TeamMemberResponse;
import com.greedy.homepage.project.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private CommonLogInformation commonLogInformation;

    @Nested
    @DisplayName("전체 프로젝트 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("프로젝트가 존재하면 200 상태코드와 목록을 반환한다")
        void success_withExistingProjects() throws Exception {
            // given
            ProjectListResponse response = new ProjectListResponse(
                    1L, "그리디 홈페이지", "홈페이지 프로젝트",
                    "https://example.com/thumbnail.png", 1
            );

            given(projectService.findAll()).willReturn(List.of(response));

            // when & then
            mockMvc.perform(get("/projects"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(1))
                    .andExpect(jsonPath("$.items[0].name").value("그리디 홈페이지"))
                    .andExpect(jsonPath("$.items[0].generationNumber").value(1));
        }

        @Test
        @DisplayName("프로젝트가 없으면 200 상태코드와 빈 목록을 반환한다")
        void success_emptyList() throws Exception {
            // given
            given(projectService.findAll()).willReturn(List.of());

            // when & then
            mockMvc.perform(get("/projects"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(0));
        }
    }

    @Nested
    @DisplayName("프로젝트 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 프로젝트 ID로 조회하면 200 상태코드와 상세 정보를 반환한다")
        void success_withExistingProjectId() throws Exception {
            // given
            TeamMemberResponse teamMember = new TeamMemberResponse(
                    1L, "김철수", "https://github.com/hong", StackPosition.BACKEND
            );
            ProjectDetailResponse detailResponse = new ProjectDetailResponse(
                    1L, "그리디 홈페이지", "홈페이지 프로젝트",
                    "https://example.com/thumbnail.png", 1,
                    "동아리 홍보", "프로젝트 관리",
                    ProjectType.GENERATION,
                    "https://greedy.com", "https://github.com/backend", "https://github.com/frontend",
                    List.of("Spring Boot", "JPA"), List.of("React"),
                    List.of("https://example.com/screenshot.png"),
                    List.of(teamMember)
            );

            given(projectService.findById(1L)).willReturn(detailResponse);

            // when & then
            mockMvc.perform(get("/projects/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("그리디 홈페이지"))
                    .andExpect(jsonPath("$.generationNumber").value(1))
                    .andExpect(jsonPath("$.backendStack.length()").value(2))
                    .andExpect(jsonPath("$.screenshotUrls.length()").value(1))
                    .andExpect(jsonPath("$.team.length()").value(1))
                    .andExpect(jsonPath("$.team[0].name").value("김철수"));
        }

        @Test
        @DisplayName("존재하지 않는 프로젝트 ID로 조회하면 404 상태코드를 반환한다")
        void error_notFoundProject() throws Exception {
            // given
            given(projectService.findById(999L))
                    .willThrow(new HomepageException(FailMessage.NOT_FOUND_PROJECT));

            // when & then
            mockMvc.perform(get("/projects/{id}", 999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(40404))
                    .andExpect(jsonPath("$.message").value("프로젝트를 찾을 수 없습니다."));
        }

        @Test
        @DisplayName("잘못된 타입의 ID로 조회하면 400 상태코드를 반환한다")
        void error_invalidIdType() throws Exception {
            // when & then
            mockMvc.perform(get("/projects/{id}", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(40003));
        }
    }
}
