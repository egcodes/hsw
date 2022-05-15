package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.persistence.repository.PersonRepository;
import com.hackerswork.hsw.service.person.PersonCommandService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonCommandServiceImpl implements PersonCommandService {

    private final PersonRepository personRepository;

    @Override
    public Person add(Person person) {
        person.setCreateDate(OffsetDateTime.now());
        person.setStatus(Status.ACTIVE);
        return personRepository.save(person);
    }


}
