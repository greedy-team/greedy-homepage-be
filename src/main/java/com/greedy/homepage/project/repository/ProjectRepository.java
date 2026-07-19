package com.greedy.homepage.project.repository;

import com.greedy.homepage.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
