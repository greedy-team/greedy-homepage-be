package com.greedy.homepage.generation.repository;

import com.greedy.homepage.generation.domain.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
}
