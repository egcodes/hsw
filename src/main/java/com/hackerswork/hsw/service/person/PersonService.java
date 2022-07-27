package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.dto.SettingsDTO;

public interface PersonService {

    PersonDataDTO find(Long id);

    PersonDataDTO find(String userName);

    boolean setUserSettings(Long id, SettingsDTO settingsDTO);
}
