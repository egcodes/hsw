package com.hackerswork.hsw.service.person;

import com.hackerswork.hsw.dto.PersonDataDTO;

public interface PersonService {

    PersonDataDTO find(Long id);

    PersonDataDTO find(String userName);
}
