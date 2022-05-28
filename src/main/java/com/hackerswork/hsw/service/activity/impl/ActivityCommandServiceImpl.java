package com.hackerswork.hsw.service.activity.impl;

import com.hackerswork.hsw.persistence.entity.Activity;
import com.hackerswork.hsw.persistence.repository.ActivityRepository;
import com.hackerswork.hsw.service.activity.ActivityCommandService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityCommandServiceImpl implements ActivityCommandService {

    private final ActivityRepository activityRepository;

    @Override
    public Activity upsert(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void updateLastActivityTimeByPersonId(Long personId) {
        var personActivity = activityRepository.findByPersonId(personId);
        personActivity.setLastActivityTime(OffsetDateTime.now().toEpochSecond());
        activityRepository.save(personActivity);
    }

}
