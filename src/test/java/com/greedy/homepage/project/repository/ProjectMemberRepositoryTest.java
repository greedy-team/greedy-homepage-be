package com.greedy.homepage.project.repository;

import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.generation.repository.GenerationRepository;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.repository.MemberRepository;
import com.greedy.homepage.project.domain.Project;
import com.greedy.homepage.project.domain.ProjectMember;
import com.greedy.homepage.support.ServiceIntegrationTest;
import com.greedy.homepage.support.fixture.GenerationFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectFixtureBuilder;
import com.greedy.homepage.support.fixture.ProjectMemberFixtureBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ProjectMemberRepositoryTest extends ServiceIntegrationTest {

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GenerationRepository generationRepository;

    private Generation generation;
    private Project project1;
    private Project project2;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUpData() {
        generation = generationRepository.save(GenerationFixtureBuilder.builder().number(1).build());
        project1 = projectRepository.save(ProjectFixtureBuilder.withGeneration(generation).name("프로젝트A").build());
        project2 = projectRepository.save(ProjectFixtureBuilder.withGeneration(generation).name("프로젝트B").build());
        member1 = memberRepository.save(MemberFixtureBuilder.builder().name("김철수").build());
        member2 = memberRepository.save(MemberFixtureBuilder.builder().name("이영희").build());
    }

    @Nested
    @DisplayName("멤버 ID로 프로젝트 멤버를 조회할 때")
    class FindAllByMemberId {

        @Test
        @DisplayName("해당 멤버가 참여한 프로젝트만 반환한다")
        void success_withExistingMemberId() {
            // given
            projectMemberRepository.save(
                    ProjectMemberFixtureBuilder.withProjectAndMember(project1, member1).build()
            );
            projectMemberRepository.save(
                    ProjectMemberFixtureBuilder.withProjectAndMember(project2, member2).build()
            );

            // when
            List<ProjectMember> result = projectMemberRepository.findAllByMemberId(member1.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(1);
                softly.assertThat(result.get(0).getProject().getId()).isEqualTo(project1.getId());
            });
        }

        @Test
        @DisplayName("참여한 프로젝트가 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // when
            List<ProjectMember> result = projectMemberRepository.findAllByMemberId(member1.getId());

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("프로젝트 ID로 프로젝트 멤버를 조회할 때")
    class FindAllByProjectId {

        @Test
        @DisplayName("해당 프로젝트의 멤버만 반환한다")
        void success_withExistingProjectId() {
            // given
            projectMemberRepository.save(
                    ProjectMemberFixtureBuilder.withProjectAndMember(project1, member1).build()
            );
            projectMemberRepository.save(
                    ProjectMemberFixtureBuilder.withProjectAndMember(project1, member2).build()
            );
            projectMemberRepository.save(
                    ProjectMemberFixtureBuilder.withProjectAndMember(project2, member1).build()
            );

            // when
            List<ProjectMember> result = projectMemberRepository.findAllByProjectId(project1.getId());

            // then
            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("멤버가 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // when
            List<ProjectMember> result = projectMemberRepository.findAllByProjectId(project1.getId());

            // then
            assertThat(result).isEmpty();
        }
    }
}
