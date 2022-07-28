package com.hackerswork.hsw.service.person.impl;

import static java.util.Objects.nonNull;

import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Activity;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.persistence.repository.PersonRepository;
import com.hackerswork.hsw.service.activity.ActivityCommandService;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonCommandServiceImpl implements PersonCommandService {

    private final PersonRepository personRepository;
    private final PersonQueryService personQueryService;
    private final ActivityCommandService activityCommandService;

    @Override
    public Person add(Person person) {
        Person existsPerson = null;
        try {
             existsPerson = personQueryService.findByUserName(person.getUserName());
        } catch (HswException ignored) {
        }
        var isExists = nonNull(existsPerson);

        if (isExists) {
            if (existsPerson.getStatus().equals(Status.ACTIVE)) {
                return existsPerson;
            } else {
                existsPerson.setStatus(Status.ACTIVE);
                existsPerson.setName(person.getName());
                existsPerson.setMail(person.getMail());
                person = existsPerson;
            }
        }

        var now = Instant.now();
        person.setCreateDate(now);
        var personData = personRepository.save(person);

        if (!isExists) {
            var activity = Activity.builder()
                .personId(personData.getId())
                .lastActivityTime(now.toEpochMilli())
                .build();
            activityCommandService.save(activity);
        }

        return person;
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }

}
