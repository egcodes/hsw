package com.hackerupdates.hsw.service.person;

import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.domain.entity.Person;
import com.hackerupdates.hsw.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonQueryService {

    private final PersonRepository personRepository;

    public Person find(Long id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    public Person findByUserName(String userName) {
        return personRepository.findByUserName(userName)
            .orElseThrow(() -> new HswException(ValidationRule.PERSON_NOT_FOUND));
    }

    public List<Person> findByUserNameLike(Status status, String searchText) {
        return personRepository.findByStatusAndUserNameContainingIgnoreCase(status, searchText);
    }

    public List<Person> findByNameLike(Status status, String searchText) {
        return personRepository.findByStatusAndNameContainingIgnoreCase(status, searchText);
    }

    public List<Person> findAllByUserName(List<String> userNames) {
        return personRepository.findAllByUserNameIn(userNames);
    }

}
