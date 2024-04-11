package com.hackerupdates.hsw.service.person;

import com.hackerupdates.hsw.domain.dto.PersonDataDTO;
import com.hackerupdates.hsw.domain.dto.SettingsDTO;
import com.hackerupdates.hsw.domain.entity.Person;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonQueryService personQueryService;
    private final PersonCommandService personCommandService;
    private final ConnectionQueryService connectionQueryService;

    public PersonDataDTO find(Long id) {
        var person = personQueryService.find(id);
        return getPersonData(person);
    }

    public PersonDataDTO find(String userName) {
        var person = personQueryService.findByUserName(userName);
        return getPersonData(person);
    }

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
