package com.hackerupdates.hsw.service.activity;

import com.hackerupdates.hsw.domain.entity.Activity;
import com.hackerupdates.hsw.domain.repository.ActivityRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ActivityCommandService {

    private final ActivityRepository activityRepository;

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public void updateLastActivityTimeByPersonId(Long personId) {
        activityRepository.updateLastActivityTimeByPersonId(personId, Instant.now().toEpochMilli());
    }

}
