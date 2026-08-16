package com.greedy.homepage.support.fixture;

import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.member.domain.BaseMember;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.ExternalMemberRole;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberActionFixtureBuilder {

    private final BaseMember member;
    private MemberRole memberRole = MemberRole.STUDY_MEMBER;
    private ExternalMemberRole externalMemberRole;
    private StackPosition stackPosition = StackPosition.BACKEND;
    private Generation generation;

    private MemberActionFixtureBuilder(BaseMember member) {
        this.member = member;
    }

    public static MemberActionFixtureBuilder withMember(Member member) {
        return new MemberActionFixtureBuilder(member);
    }

    public static MemberActionFixtureBuilder withExternalMember(BaseMember member) {
        MemberActionFixtureBuilder builder = new MemberActionFixtureBuilder(member);
        builder.memberRole = null;
        builder.externalMemberRole = ExternalMemberRole.REVIEWER;
        return builder;
    }

    public MemberActionFixtureBuilder memberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
        return this;
    }

    public MemberActionFixtureBuilder externalMemberRole(ExternalMemberRole externalMemberRole) {
        this.externalMemberRole = externalMemberRole;
        return this;
    }

    public MemberActionFixtureBuilder stackPosition(StackPosition stackPosition) {
        this.stackPosition = stackPosition;
        return this;
    }

    public MemberActionFixtureBuilder generation(Generation generation) {
        this.generation = generation;
        return this;
    }

    public MemberAction build() {
        return MemberAction.builder()
                .member(member)
                .memberRole(memberRole)
                .externalMemberRole(externalMemberRole)
                .stackPosition(stackPosition)
                .generation(generation)
                .build();
    }

    public MemberAction buildWithId(Long id) {
        MemberAction memberAction = build();
        ReflectionTestUtils.setField(memberAction, "id", id);
        return memberAction;
    }
}
