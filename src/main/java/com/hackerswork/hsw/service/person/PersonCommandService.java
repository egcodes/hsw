package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;

public interface PersonCommandService {

    Person add(Person ad);

    Person addPartial(String userName);

    void update(Person person);

}
