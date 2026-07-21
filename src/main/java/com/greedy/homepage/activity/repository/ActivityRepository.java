package com.greedy.homepage.activity.repository;

import com.greedy.homepage.activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByOrderByStartDateDesc();
}
