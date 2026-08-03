package com.greedy.homepage.member.repository;

import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.generation.repository.GenerationRepository;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.support.ServiceIntegrationTest;
import com.greedy.homepage.support.fixture.GenerationFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberActionFixtureBuilder;
import com.greedy.homepage.support.fixture.MemberFixtureBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MemberActionRepositoryTest extends ServiceIntegrationTest {

    @Autowired
    private MemberActionRepository memberActionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GenerationRepository generationRepository;

    private Member member1;
    private Member member2;
    private Generation generation;

    @BeforeEach
    void setUpData() {
        member1 = memberRepository.save(MemberFixtureBuilder.builder().name("김철수").build());
        member2 = memberRepository.save(MemberFixtureBuilder.builder().name("이영희").build());
        generation = generationRepository.save(GenerationFixtureBuilder.builder().number(1).build());
    }

    @Nested
    @DisplayName("멤버 ID로 활동을 조회할 때")
    class FindAllByMemberId {

        @Test
        @DisplayName("해당 멤버의 활동만 반환한다")
        void success_withExistingMemberId() {
            // given
            memberActionRepository.save(
                    MemberActionFixtureBuilder.withMember(member1).generation(generation).build()
            );
            memberActionRepository.save(
                    MemberActionFixtureBuilder.withMember(member2).generation(generation).build()
            );

            // when
            List<MemberAction> result = memberActionRepository.findAllByMemberId(member1.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(1);
                softly.assertThat(result.get(0).getMember().getId()).isEqualTo(member1.getId());
            });
        }

        @Test
        @DisplayName("활동이 없으면 빈 목록을 반환한다")
        void success_emptyList() {
            // when
            List<MemberAction> result = memberActionRepository.findAllByMemberId(member1.getId());

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("멤버 ID 목록으로 활동을 일괄 조회할 때")
    class FindAllByMemberIdIn {

        @Test
        @DisplayName("해당 멤버들의 모든 활동을 반환한다")
        void success_withMultipleMemberIds() {
            // given
            memberActionRepository.save(
                    MemberActionFixtureBuilder.withMember(member1).generation(generation).build()
            );
            memberActionRepository.save(
                    MemberActionFixtureBuilder.withMember(member2).generation(generation).build()
            );

            // when
            List<MemberAction> result = memberActionRepository.findAllByMemberIdIn(
                    List.of(member1.getId(), member2.getId())
            );

            // then
            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("빈 ID 목록이면 빈 결과를 반환한다")
        void success_emptyIdList() {
            // when
            List<MemberAction> result = memberActionRepository.findAllByMemberIdIn(List.of());

            // then
            assertThat(result).isEmpty();
        }
    }
}
