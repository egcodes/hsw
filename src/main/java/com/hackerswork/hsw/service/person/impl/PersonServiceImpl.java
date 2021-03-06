package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonServiceImpl implements PersonService {

    private final PersonQueryService personQueryService;
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
            .build();
    }
}
