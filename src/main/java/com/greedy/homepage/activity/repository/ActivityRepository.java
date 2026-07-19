package com.greedy.homepage.activity.repository;

import com.greedy.homepage.activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
