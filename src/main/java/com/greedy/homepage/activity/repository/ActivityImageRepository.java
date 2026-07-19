package com.greedy.homepage.activity.repository;

import com.greedy.homepage.activity.domain.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, Long> {
}
