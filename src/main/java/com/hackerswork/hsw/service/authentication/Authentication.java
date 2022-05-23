package com.hackerswork.hsw.service.authentication;

import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.persistence.entity.Person;

public interface Authentication {

    Person login(Auth auth, String code);

}
