package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;

public interface PersonCommandService {

    Person add(Person ad);

    void update(Person person);

}
