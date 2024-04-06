package com.hackerupdates.hsw.service.person.impl;

import com.hackerupdates.hsw.dto.PersonDataDTO;
import com.hackerupdates.hsw.dto.SettingsDTO;
import com.hackerupdates.hsw.persistence.entity.Person;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import com.hackerupdates.hsw.service.person.PersonCommandService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonQueryService personQueryService;
    private final PersonCommandService personCommandService;
    private final ConnectionQueryService connectionQueryService;

    @Override
    public PersonDataDTO find(Long id) {
        var person = personQueryService.find(id);
        return getPersonData(person);
    }

    @Override
    public PersonDataDTO find(String userName) {
        var person = personQueryService.findByUserName(userName);
        return getPersonData(person);
    }

    @Override
    public boolean setUserSettings(Long id, SettingsDTO settingsDTO) {
        var person = personQueryService.find(id);
        person.setDarkTheme(settingsDTO.isDarkTheme());
        personCommandService.update(person);
        return Boolean.TRUE;
    }

    private PersonDataDTO getPersonData(Person person) {
        var followingCount = connectionQueryService.findConnectionIds(person.getId()).size();
        var followersCount = connectionQueryService.findNumOfFollowers(person.getId());

        return PersonDataDTO.builder()
            .name(person.getName())
            .userName(person.getUserName())
            .mail(person.getMail())
            .followers(followersCount)
            .following(followingCount)
            .status(person.getStatus())
            .createDate(person.getCreateDate())
            .about(person.getAbout())
            .darkTheme(person.isDarkTheme())
            .build();
    }
}
