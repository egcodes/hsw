package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.persistence.repository.PersonRepository;
import com.hackerswork.hsw.service.person.PersonQueryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonQueryServiceImpl implements PersonQueryService {

    private final PersonRepository personRepository;

    @Override
    public Optional<Person> findPerson(Long id) {
        var personPossible = personRepository.findById(id);
        if (personPossible.isPresent())
            return personPossible;
        else
            throw new HswException(ValidationRule.PERSON_NOT_FOUND);
    }

    @Override
    public Optional<Person> findPersonByUserName(String userName) {
        return Optional.ofNullable(personRepository.findByUserName(userName));
    }

    @Override
    public List<Person> findPersonsByUserName(List<String> userNames) {
        return personRepository.findAllByUserNameIn(userNames);
    }

}
