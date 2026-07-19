package com.greedy.homepage.member.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.member.domain.enums.Department;
import com.greedy.homepage.member.domain.enums.StackPosition;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

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
    @Column(nullable = false)
    private StackPosition mainStackPosition;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_department", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private List<Department> departments = new ArrayList<>();

    @Builder
    public Member(String name, String githubUrl, String imageUrl, String description, StackPosition mainStackPosition) {
        this.name = name;
        this.githubUrl = githubUrl;
        this.imageUrl = imageUrl;
        this.description = description;
        this.mainStackPosition = mainStackPosition;
    }
}
