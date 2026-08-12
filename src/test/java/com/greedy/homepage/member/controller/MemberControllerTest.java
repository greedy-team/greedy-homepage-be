package com.greedy.homepage.member.controller;

import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.common.log.CommonLogInformation;
import com.greedy.homepage.member.dto.MemberActionResponse;
import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import com.greedy.homepage.member.dto.TeamProjectResponse;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.member.service.MemberService;
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

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @MockitoBean
    private CommonLogInformation commonLogInformation;

    @Nested
    @DisplayName("전체 멤버 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("멤버가 존재하면 200 상태코드와 목록을 반환한다")
        void success_withExistingMembers() throws Exception {
            // given
            MemberActionResponse action = new MemberActionResponse(
                    MemberRole.MAINTAINER, StackPosition.BACKEND, 1
            );
            MemberListResponse memberResponse = new MemberListResponse(
                    1L, "김철수", "https://github.com/kimcs", List.of("컴퓨터공학과"), List.of(action)
            );

            given(memberService.findAll()).willReturn(List.of(memberResponse));

            // when & then
            mockMvc.perform(get("/members"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(1))
                    .andExpect(jsonPath("$.items[0].name").value("김철수"))
                    .andExpect(jsonPath("$.items[0].departmentKoreanNames[0]").value("컴퓨터공학과"))
                    .andExpect(jsonPath("$.items[0].memberActions.length()").value(1));
        }

        @Test
        @DisplayName("멤버가 없으면 200 상태코드와 빈 목록을 반환한다")
        void success_emptyList() throws Exception {
            // given
            given(memberService.findAll()).willReturn(List.of());

            // when & then
            mockMvc.perform(get("/members"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(0));
        }
    }

    @Nested
    @DisplayName("멤버 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 멤버 ID로 조회하면 200 상태코드와 상세 정보를 반환한다")
        void success_withExistingMemberId() throws Exception {
            // given
            MemberActionResponse action = new MemberActionResponse(
                    MemberRole.STUDY_MEMBER, StackPosition.BACKEND, 1
            );
            TeamProjectResponse teamProject = new TeamProjectResponse(
                    1L, "그리디 홈페이지", StackPosition.BACKEND
            );
            MemberDetailResponse detailResponse = new MemberDetailResponse(
                    1L, "김철수", "https://github.com/kimcs", List.of("컴퓨터공학과", "AI로봇학과"),
                    List.of(action), "백엔드 개발자", List.of(teamProject)
            );

            given(memberService.findById(1L)).willReturn(detailResponse);

            // when & then
            mockMvc.perform(get("/members/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("김철수"))
                    .andExpect(jsonPath("$.departments[0]").value("컴퓨터공학과"))
                    .andExpect(jsonPath("$.departments[1]").value("AI로봇학과"))
                    .andExpect(jsonPath("$.description").value("백엔드 개발자"))
                    .andExpect(jsonPath("$.memberActions.length()").value(1))
                    .andExpect(jsonPath("$.teamProjects.length()").value(1))
                    .andExpect(jsonPath("$.teamProjects[0].name").value("그리디 홈페이지"));
        }

        @Test
        @DisplayName("존재하지 않는 멤버 ID로 조회하면 404 상태코드를 반환한다")
        void error_notFoundMember() throws Exception {
            // given
            given(memberService.findById(999L))
                    .willThrow(new HomepageException(FailMessage.NOT_FOUND_MEMBER));

            // when & then
            mockMvc.perform(get("/members/{id}", 999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(40402))
                    .andExpect(jsonPath("$.message").value("멤버를 찾을 수 없습니다."));
        }

        @Test
        @DisplayName("잘못된 타입의 ID로 조회하면 400 상태코드를 반환한다")
        void error_invalidIdType() throws Exception {
            // when & then
            mockMvc.perform(get("/members/{id}", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(40003));
        }
    }
}
