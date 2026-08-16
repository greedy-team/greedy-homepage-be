package com.greedy.homepage.member.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.member.domain.enums.StackPosition;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "member_type")
@Table(name = "member")
public abstract class BaseMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String githubUrl;

    @Column
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_stack_position")
    private StackPosition mainStackPosition;

    protected BaseMember(String name, String githubUrl, String imageUrl, String description, StackPosition mainStackPosition) {
        this.name = name;
        this.githubUrl = githubUrl;
        this.imageUrl = imageUrl;
        this.description = description;
        this.mainStackPosition = mainStackPosition;
    }
}
