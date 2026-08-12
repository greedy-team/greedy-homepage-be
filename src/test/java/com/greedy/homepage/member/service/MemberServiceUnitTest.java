package com.greedy.homepage.member.service;

import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.Department;
import com.greedy.homepage.member.domain.enums.StackPosition;
import com.greedy.homepage.member.dto.MemberDetailResponse;
import com.greedy.homepage.member.dto.MemberListResponse;
import com.greedy.homepage.member.repository.BaseMemberRepository;
import com.greedy.homepage.member.repository.MemberActionRepository;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.repository.ProjectMemberRepository;
import com.greedy.homepage.support.fixture.GenerationFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberActionFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectMemberFixtureBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private BaseMemberRepository baseMemberRepository;

    @Mock
    private MemberActionRepository memberActionRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Nested
    @DisplayName("전체 멤버 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("멤버가 존재하면 멤버 목록과 각 멤버의 활동 정보를 반환한다")
        void success_withExistingMembers() {
            // given
            Member member1 = MemberFixtureBuilder.builder()
                    .name("김철수")
                    .departments(List.of(Department.COMPUTER_SCIENCES_AND_ENGINEERING))
                    .buildWithId(1L);
            Member member2 = MemberFixtureBuilder.builder()
                    .name("이영희")
                    .mainStackPosition(StackPosition.FRONTEND)
                    .departments(List.of(Department.BUSINESS_ADMINISTRATION))
                    .buildWithId(2L);

            Generation generation = GenerationFixtureBuilder.builder()
                    .number(1)
                    .buildWithId(1L);

            MemberAction action1 = MemberActionFixtureBuilder.withMember(member1)
                    .generation(generation)
                    .buildWithId(1L);

            given(baseMemberRepository.findAll()).willReturn(List.of(member1, member2));
            given(memberActionRepository.findAllByMemberIdIn(List.of(1L, 2L)))
                    .willReturn(List.of(action1));

            // when
            List<MemberListResponse> result = memberService.findAll();

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(2);
                softly.assertThat(result.get(0).name()).isEqualTo("김철수");
                softly.assertThat(result.get(0).departmentKoreanNames()).containsExactly("컴퓨터공학과");
                softly.assertThat(result.get(0).memberActions()).hasSize(1);
                softly.assertThat(result.get(1).name()).isEqualTo("이영희");
                softly.assertThat(result.get(1).departmentKoreanNames()).containsExactly("경영학부");
                softly.assertThat(result.get(1).memberActions()).isEmpty();
            });
        }

        @Test
        @DisplayName("멤버가 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // given
            given(baseMemberRepository.findAll()).willReturn(List.of());

            // when
            List<MemberListResponse> result = memberService.findAll();

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("멤버 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 멤버 ID로 조회하면 상세 정보를 반환한다")
        void success_withExistingMemberId() {
            // given
            Member member = MemberFixtureBuilder.builder()
                    .name("김철수")
                    .description("백엔드 개발자")
                    .departments(List.of(Department.COMPUTER_SCIENCES_AND_ENGINEERING, Department.ARTIFICIAL_INTELLIGENCE_AND_ROBOTICS))
                    .buildWithId(1L);

            Generation generation = GenerationFixtureBuilder.builder()
                    .number(1)
                    .buildWithId(1L);

            MemberAction action = MemberActionFixtureBuilder.withMember(member)
                    .generation(generation)
                    .buildWithId(1L);

            Project project = ProjectFixtureBuilder.withGeneration(generation)
                    .name("그리디 홈페이지")
                    .buildWithId(1L);

            ProjectMember projectMember = ProjectMemberFixtureBuilder
                    .withProjectAndMember(project, member)
                    .buildWithId(1L);

            given(baseMemberRepository.findById(1L)).willReturn(Optional.of(member));
            given(memberActionRepository.findAllByMemberId(1L)).willReturn(List.of(action));
            given(projectMemberRepository.findAllByMemberId(1L)).willReturn(List.of(projectMember));

            // when
            MemberDetailResponse result = memberService.findById(1L);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.name()).isEqualTo("김철수");
                softly.assertThat(result.departmentKoreanNames()).containsExactly("컴퓨터공학과", "AI로봇학과");
                softly.assertThat(result.description()).isEqualTo("백엔드 개발자");
                softly.assertThat(result.memberActions()).hasSize(1);
                softly.assertThat(result.teamProjects()).hasSize(1);
                softly.assertThat(result.teamProjects().get(0).name()).isEqualTo("그리디 홈페이지");
            });
        }

        @Test
        @DisplayName("존재하지 않는 멤버 ID로 조회하면 예외가 발생한다")
        void error_notFoundMember() {
            // given
            given(baseMemberRepository.findById(999L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> memberService.findById(999L))
                    .isInstanceOf(HomepageException.class)
                    .hasMessage("멤버를 찾을 수 없습니다.");
        }
    }
}
