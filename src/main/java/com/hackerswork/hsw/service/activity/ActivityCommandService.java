package com.hackerswork.hsw.service.activity;

import com.hackerswork.hsw.persistence.entity.Activity;

public interface ActivityCommandService {

    Activity save(Activity activity);

    void updateLastActivityTimeByPersonId(Long personId);

}
