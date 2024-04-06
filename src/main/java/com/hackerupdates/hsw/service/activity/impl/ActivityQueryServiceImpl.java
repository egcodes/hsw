package com.hackerupdates.hsw.service.activity.impl;

import com.hackerupdates.hsw.dto.ActivityDTO;
import com.hackerupdates.hsw.persistence.repository.ActivityRepository;
import com.hackerupdates.hsw.service.activity.ActivityQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;

    @Override
    public List<ActivityDTO> list(List<Long> personIds) {
        return activityRepository.findAllByPersonIds(personIds);
    }

}
