package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;
import java.util.List;
import java.util.Optional;

public interface PersonQueryService {

    Optional<Person> findPerson(Long id);

    Optional<Person> findPersonByUserName(String userName);

    List<Person> findPersonsByUserName(List<String> userNames);

}
