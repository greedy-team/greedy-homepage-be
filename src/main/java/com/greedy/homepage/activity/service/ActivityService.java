package com.greedy.homepage.activity.service;

import com.greedy.homepage.activity.domain.Activity;
import com.greedy.homepage.activity.domain.ActivityImage;
import com.greedy.homepage.activity.dto.ActivityDetailResponse;
import com.greedy.homepage.activity.dto.ActivityListResponse;
import com.greedy.homepage.activity.repository.ActivityImageRepository;
import com.greedy.homepage.activity.repository.ActivityRepository;
import com.greedy.homepage.common.exception.FailMessage;
import com.greedy.homepage.common.exception.HomepageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityImageRepository activityImageRepository;

    public List<ActivityListResponse> findAll() {
        List<Activity> activities = activityRepository.findAllByOrderByStartDateDesc();
        List<Long> activityIds = activities.stream().map(Activity::getId).toList();

        Map<Long, List<ActivityImage>> imagesByActivityId = activityImageRepository.findAllByActivityIdIn(activityIds)
                .stream()
                .collect(Collectors.groupingBy(image -> image.getActivity().getId()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().limit(3).toList()
                ));

        return activities.stream()
                .map(activity -> ActivityListResponse.of(
                        activity,
                        imagesByActivityId.getOrDefault(activity.getId(), List.of())
                ))
                .toList();
    }

    public ActivityDetailResponse findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new HomepageException(FailMessage.NOT_FOUND_ACTIVITY));

        List<ActivityImage> images = activityImageRepository.findAllByActivityId(id);

        return ActivityDetailResponse.of(activity, images);
    }
}
