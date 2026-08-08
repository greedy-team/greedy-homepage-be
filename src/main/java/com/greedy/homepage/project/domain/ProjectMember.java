package com.greedy.homepage.project.domain;

import com.greedy.homepage.common.domain.BaseEntity;
import com.greedy.homepage.member.domain.BaseMember;
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

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE project_member SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "project_member")
public class ProjectMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private BaseMember member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StackPosition stackPosition;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Builder
    public ProjectMember(
            Project project,
            BaseMember member,
            StackPosition stackPosition,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.project = project;
        this.member = member;
        this.stackPosition = stackPosition;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
