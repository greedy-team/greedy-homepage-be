package com.greedy.homepage.support.fixture;

import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.enums.StackPosition;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixtureBuilder {

    private String name = "홍길동";
    private String githubUrl = "https://github.com/honggildong";
    private String imageUrl = "https://example.com/image.png";
    private String description = "백엔드 개발자입니다.";
    private StackPosition mainStackPosition = StackPosition.BACKEND;

    public static MemberFixtureBuilder builder() {
        return new MemberFixtureBuilder();
    }

    public MemberFixtureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MemberFixtureBuilder githubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
        return this;
    }

    public MemberFixtureBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public MemberFixtureBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MemberFixtureBuilder mainStackPosition(StackPosition mainStackPosition) {
        this.mainStackPosition = mainStackPosition;
        return this;
    }

    public Member build() {
        return Member.builder()
                .name(name)
                .githubUrl(githubUrl)
                .imageUrl(imageUrl)
                .description(description)
                .mainStackPosition(mainStackPosition)
                .build();
    }

    public Member buildWithId(Long id) {
        Member member = build();
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
