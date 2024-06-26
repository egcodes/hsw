package com.hackerupdates.hsw.service.person;

import static java.util.Objects.nonNull;

import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.domain.entity.Activity;
import com.hackerupdates.hsw.domain.entity.Person;
import com.hackerupdates.hsw.domain.repository.PersonRepository;
import com.hackerupdates.hsw.service.activity.ActivityCommandService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PersonCommandService {

    private final PersonRepository personRepository;
    private final PersonQueryService personQueryService;
    private final ActivityCommandService activityCommandService;

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

    public void update(Person person) {
        personRepository.save(person);
    }

}
