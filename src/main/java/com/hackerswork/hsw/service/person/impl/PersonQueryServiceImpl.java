package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.persistence.repository.PersonRepository;
import com.hackerswork.hsw.service.person.PersonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonQueryServiceImpl implements PersonQueryService {

    private final PersonRepository personRepository;

    @Override
    public Person findPerson(Long id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    @Override
    public Person findPersonByUserName(String userName) {
        return personRepository.findByUserName(userName)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    @Override
    public List<Person> findPersonsByUserName(List<String> userNames) {
        return personRepository.findAllByUserNameIn(userNames);
    }

}
