package com.hackerswork.hsw.service.activity;

import com.hackerswork.hsw.dto.ActivityDTO;
import java.util.List;

public interface ActivityQueryService {

    List<ActivityDTO> list(List<Long> personIds);

}
