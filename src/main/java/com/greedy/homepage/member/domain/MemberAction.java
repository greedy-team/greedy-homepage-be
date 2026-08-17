package com.greedy.homepage.member.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.generation.domain.Generation;
import com.greedy.homepage.member.domain.enums.ExternalMemberRole;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member_action SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "member_action")
public class MemberAction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private BaseMember member;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column
    private ExternalMemberRole externalMemberRole;

    @Enumerated(EnumType.STRING)
    private StackPosition stackPosition;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @Builder
    public MemberAction(BaseMember member, MemberRole memberRole, ExternalMemberRole externalMemberRole, StackPosition stackPosition, Generation generation) {
        this.member = member;
        this.memberRole = memberRole;
        this.externalMemberRole = externalMemberRole;
        this.stackPosition = stackPosition;
        this.generation = generation;
    }

    public Integer getGenerationNumber() {
        if (generation == null) {
            return null;
        }
        return generation.getNumber();
    }
}
