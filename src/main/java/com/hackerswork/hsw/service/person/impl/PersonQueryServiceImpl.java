package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.persistence.repository.PersonRepository;
import com.hackerswork.hsw.service.person.PersonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonQueryServiceImpl implements PersonQueryService {

    private final PersonRepository personRepository;

    @Override
    public Person getPerson(Long id) {
        var personPossible = personRepository.findById(id);
        if (personPossible.isPresent())
            return personPossible.get();
        else
            throw new HswException(ValidationRule.PERSON_NOT_FOUND);
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

}
