package com.hackerupdates.hsw.service.activity;

import com.hackerupdates.hsw.domain.dto.ActivityDTO;
import com.hackerupdates.hsw.domain.repository.ActivityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityQueryService {

    private final ActivityRepository activityRepository;

    public List<ActivityDTO> list(List<Long> personIds) {
        return activityRepository.findAllByPersonIds(personIds);
    }

}
