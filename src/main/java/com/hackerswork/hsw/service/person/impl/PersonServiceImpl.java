package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.dto.PersonDataDTO;
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
        var followingCount = connectionQueryService.findConnectionIds(id).size();
        var followersCount = connectionQueryService.findNumOfFollowers(id);

        return PersonDataDTO.builder()
            .name(person.getName())
            .userName(person.getUserName())
            .mail(person.getMail())
            .followers(followersCount)
            .following(followingCount)
            .status(person.getStatus())
            .createDate(person.getCreateDate())
            .build();
    }
}
