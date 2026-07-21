package com.greedy.homepage.project.repository;

import com.greedy.homepage.project.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findAllByMemberId(Long memberId);

    List<ProjectMember> findAllByProjectId(Long projectId);
}
