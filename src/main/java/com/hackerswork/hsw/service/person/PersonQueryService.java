package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;
import java.util.List;

public interface PersonQueryService {

    Person getPerson(Long id);

    List<Person> getPersons();

}
