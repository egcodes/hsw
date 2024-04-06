package com.hackerupdates.hsw.service.activity;

import com.hackerupdates.hsw.dto.ActivityDTO;
import java.util.List;

public interface ActivityQueryService {

    List<ActivityDTO> list(List<Long> personIds);

}
