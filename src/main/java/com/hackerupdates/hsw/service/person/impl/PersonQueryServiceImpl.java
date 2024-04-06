package com.hackerupdates.hsw.service.person.impl;

import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.persistence.entity.Person;
import com.hackerupdates.hsw.persistence.repository.PersonRepository;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
