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
             existsPerson = personQueryService.findPersonByUserName(person.getUserName());
        } catch (HswException ignored) {
        }

        if (nonNull(existsPerson)) {
            person = existsPerson;
            if (person.getStatus().equals(Status.ACTIVE)) {
                return person;
            } else {
                person.setStatus(Status.ACTIVE);
                person.setName(person.getName());
                person.setMail(person.getMail());
            }
        }

        var now = Instant.now();
        person.setCreateDate(now);
        var personData = personRepository.save(person);

        var activity = Activity.builder()
            .personId(personData.getId())
            .lastActivityTime(Status.PARTIAL.equals(person.getStatus()) ? 0L : now.toEpochMilli())
            .build();
        activityCommandService.upsert(activity);

        return person;
    }

    @Override
    public Person addPartial(String userName) {
        return add(Person.builder()
            .userName(userName)
            .status(Status.PARTIAL)
            .build());
    }

}
