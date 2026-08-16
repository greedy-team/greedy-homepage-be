package com.greedy.homepage.member.domain;

import com.greedy.homepage.member.domain.enums.Department;
import com.greedy.homepage.member.domain.enums.StackPosition;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("INTERNAL")
public class Member extends BaseMember {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_department", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private List<Department> departments = new ArrayList<>();

    @Builder
    public Member(String name, String githubUrl, String imageUrl, String description, StackPosition mainStackPosition) {
        super(name, githubUrl, imageUrl, description, mainStackPosition);
    }
}
