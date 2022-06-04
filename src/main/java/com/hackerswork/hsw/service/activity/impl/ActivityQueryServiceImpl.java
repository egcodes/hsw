package com.hackerswork.hsw.service.activity.impl;

import com.hackerswork.hsw.dto.ActivityDTO;
import com.hackerswork.hsw.persistence.repository.ActivityRepository;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;

    @Override
    public List<ActivityDTO> list(List<Long> personIds) {
        return activityRepository.findAllByPersonIds(personIds);
    }

}
