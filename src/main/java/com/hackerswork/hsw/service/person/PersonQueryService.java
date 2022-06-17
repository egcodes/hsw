package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.persistence.entity.Person;
import java.util.List;

public interface PersonQueryService {

    Person find(Long id);

    Person findByUserName(String userName);

    List<Person> findByUserNameLike(Status status, String searchText);

    List<Person> findByNameLike(Status status, String searchText);

    List<Person> findAllByUserName(List<String> userNames);

}
