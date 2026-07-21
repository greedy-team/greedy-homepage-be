package com.greedy.homepage.activity.repository;

import com.greedy.homepage.activity.domain.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, Long> {

    List<ActivityImage> findAllByActivityId(Long activityId);

    List<ActivityImage> findAllByActivityIdIn(List<Long> activityIds);
}
