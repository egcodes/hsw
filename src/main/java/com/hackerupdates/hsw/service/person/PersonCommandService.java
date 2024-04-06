package com.hackerupdates.hsw.service.person;

import com.hackerupdates.hsw.persistence.entity.Person;

public interface PersonCommandService {

    Person add(Person ad);

    void update(Person person);

}
