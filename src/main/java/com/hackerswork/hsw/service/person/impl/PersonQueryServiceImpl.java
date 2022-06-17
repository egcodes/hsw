package com.hackerswork.hsw.service.person.impl;

import com.hackerswork.hsw.enums.Status;
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
    public Person find(Long id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    @Override
    public Person findByUserName(String userName) {
        return personRepository.findByUserName(userName)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    @Override
    public List<Person> findByUserNameLike(Status status, String searchText) {
        return personRepository.findByStatusAndUserNameContainingIgnoreCase(status, searchText);
    }

    @Override
    public List<Person> findByNameLike(Status status, String searchText) {
        return personRepository.findByStatusAndNameContainingIgnoreCase(status, searchText);
    }

    @Override
    public List<Person> findAllByUserName(List<String> userNames) {
        return personRepository.findAllByUserNameIn(userNames);
    }

}
