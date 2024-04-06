package com.hackerupdates.hsw.service.person;

import com.hackerupdates.hsw.dto.PersonDataDTO;
import com.hackerupdates.hsw.dto.SettingsDTO;

public interface PersonService {

    PersonDataDTO find(Long id);

    PersonDataDTO find(String userName);

    boolean setUserSettings(Long id, SettingsDTO settingsDTO);
}
