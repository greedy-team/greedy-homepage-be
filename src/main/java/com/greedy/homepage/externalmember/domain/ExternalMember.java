package com.greedy.homepage.externalmember.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.externalmember.domain.enums.ExternalMemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SQLDelete(sql = "UPDATE external_member SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "external_member")
public class ExternalMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String githubUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExternalMemberRole externalMemberRole;

    @Builder
    public ExternalMember(String name, String githubUrl, ExternalMemberRole externalMemberRole) {
        this.name = name;
        this.githubUrl = githubUrl;
        this.externalMemberRole = externalMemberRole;
    }
}
