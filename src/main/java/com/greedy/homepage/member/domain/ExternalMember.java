package com.greedy.homepage.member.domain;

import com.greedy.homepage.member.domain.enums.ExternalMemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("EXTERNAL")
public class ExternalMember extends BaseMember {

    @Enumerated(EnumType.STRING)
    @Column
    private ExternalMemberRole externalMemberRole;

    @Builder
    public ExternalMember(String name, String githubUrl, String imageUrl, String description, StackPosition mainStackPosition, ExternalMemberRole externalMemberRole) {
        super(name, githubUrl, imageUrl, description, mainStackPosition);
        this.externalMemberRole = externalMemberRole;
    }
}
