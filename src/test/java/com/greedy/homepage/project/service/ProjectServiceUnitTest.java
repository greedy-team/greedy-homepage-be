package com.greedy.homepage.project.service;

import com.greedy.homepage.common.exception.HomepageException;
import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectImage;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.project.dto.ProjectDetailResponse;
import com.greedy.homepage.project.dto.ProjectListResponse;
import com.greedy.homepage.project.repository.ProjectMemberRepository;
import com.greedy.homepage.project.repository.ProjectRepository;
import com.greedy.homepage.support.fixture.GenerationFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectImageFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectMemberFixtureBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProjectServiceUnitTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Nested
    @DisplayName("전체 프로젝트 목록을 조회할 때")
    class FindAll {

        @Test
        @DisplayName("프로젝트가 존재하면 목록을 반환한다")
        void success_withExistingProjects() {
            // given
            Generation generation = GenerationFixtureBuilder.builder()
                    .number(1)
                    .buildWithId(1L);

            Project project1 = ProjectFixtureBuilder.withGeneration(generation)
                    .name("그리디 홈페이지")
                    .buildWithId(1L);
            Project project2 = ProjectFixtureBuilder.withGeneration(generation)
                    .name("출석 시스템")
                    .buildWithId(2L);

            given(projectRepository.findAll()).willReturn(List.of(project1, project2));

            // when
            List<ProjectListResponse> result = projectService.findAll();

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(2);
                softly.assertThat(result.get(0).name()).isEqualTo("그리디 홈페이지");
                softly.assertThat(result.get(0).generationNumber()).isEqualTo(1);
                softly.assertThat(result.get(1).name()).isEqualTo("출석 시스템");
            });
        }

        @Test
        @DisplayName("프로젝트가 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // given
            given(projectRepository.findAll()).willReturn(List.of());

            // when
            List<ProjectListResponse> result = projectService.findAll();

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("프로젝트 상세를 조회할 때")
    class FindById {

        @Test
        @DisplayName("존재하는 프로젝트 ID로 조회하면 상세 정보를 반환한다")
        void success_withExistingProjectId() {
            // given
            Generation generation = GenerationFixtureBuilder.builder()
                    .number(1)
                    .buildWithId(1L);

            Project project = ProjectFixtureBuilder.withGeneration(generation)
                    .name("그리디 홈페이지")
                    .buildWithId(1L);

            ProjectImage image = ProjectImageFixtureBuilder.withProject(project)
                    .imageUrl("https://example.com/screenshot.png")
                    .buildWithId(1L);
            ReflectionTestUtils.setField(project, "images", List.of(image));

            Member member = MemberFixtureBuilder.builder()
                    .name("김철수")
                    .buildWithId(1L);

            ProjectMember projectMember = ProjectMemberFixtureBuilder
                    .withProjectAndMember(project, member)
                    .buildWithId(1L);

            given(projectRepository.findById(1L)).willReturn(Optional.of(project));
            given(projectMemberRepository.findAllByProjectId(1L)).willReturn(List.of(projectMember));

            // when
            ProjectDetailResponse result = projectService.findById(1L);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.name()).isEqualTo("그리디 홈페이지");
                softly.assertThat(result.generationNumber()).isEqualTo(1);
                softly.assertThat(result.screenshotUrls()).hasSize(1);
                softly.assertThat(result.team()).hasSize(1);
                softly.assertThat(result.team().get(0).name()).isEqualTo("김철수");
            });
        }

        @Test
        @DisplayName("존재하지 않는 프로젝트 ID로 조회하면 예외가 발생한다")
        void error_notFoundProject() {
            // given
            given(projectRepository.findById(999L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> projectService.findById(999L))
                    .isInstanceOf(HomepageException.class)
                    .hasMessage("프로젝트를 찾을 수 없습니다.");
        }
    }
}
