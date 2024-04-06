package com.hackerupdates.hsw.service.activity;

import com.hackerupdates.hsw.persistence.entity.Activity;

public interface ActivityCommandService {

    Activity save(Activity activity);

    void updateLastActivityTimeByPersonId(Long personId);

}
