package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;
import java.util.List;

public interface PersonQueryService {

    Person find(Long id);

    Person findByUserName(String userName);

    Person findByName(String name);

    List<Person> findAllByUserName(List<String> userNames);

}
