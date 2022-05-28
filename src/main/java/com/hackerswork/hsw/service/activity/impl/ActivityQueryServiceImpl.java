package com.hackerswork.hsw.service.activity.impl;

import com.hackerswork.hsw.persistence.repository.ActivityRepository;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;

    @Override
    public boolean isOnline(Long personId) {
        var activity = activityRepository.findByPersonId(personId);
        var checkTime = OffsetDateTime.now().minusMinutes(5).toEpochSecond();
        if (activity.getLastActivityTime() > checkTime)
            return Boolean.TRUE;

        return Boolean.FALSE;
    }
}
