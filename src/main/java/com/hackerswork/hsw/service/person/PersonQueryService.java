package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.persistence.entity.Person;
import java.util.List;

public interface PersonQueryService {

    Person find(Long id);

    Person findByUserName(String userName);

    List<Person> findByUserNameLike(String searchText);

    List<Person> findByNameLike(String searchText);

    List<Person> findAllByUserName(List<String> userNames);

}
