package com.hackerswork.hsw.service.authentication.impl;

import com.hackerswork.hsw.dto.UserDTO;
import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.authentication.AuthProvider;
import com.hackerswork.hsw.service.authentication.Authentication;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationImpl implements Authentication {

    private final AuthProvider authProvider;
    private final PersonCommandService personCommandService;
    private final PersonQueryService personQueryService;
    private final TokenService tokenService;

    @Override
    public Person login(Auth auth, String code) {
        Person personInfo = null;
        var isRegistration = Boolean.FALSE;

        switch (auth) {
            case GITHUB:
                var userPossible = authProvider.login(code);
                if (userPossible.isPresent()) {
                    log.info("Login success. GitHub user info: {}", userPossible);

                    var user = userPossible.get();
                    try {
                        var person = personQueryService.findByUserName(user.getLogin());
                        if (Status.PARTIAL.equals(person.getStatus())) {
                            updatePerson(person, user);
                            personInfo = person;
                            isRegistration = Boolean.TRUE;
                        } else {
                            personInfo = person;
                        }
                    } catch (HswException e) {
                        personInfo = createPerson(user);
                        isRegistration = Boolean.TRUE;
                    }
                }
                break;
        }

        tokenService.set(personInfo.getId(), personInfo.getUserName(), code);
        if (isRegistration)
            personInfo.setStatus(Status.NEW);
        return personInfo;
    }

    @Override
    public boolean logout(String code) {
        return tokenService.evict(code);
    }

    private void updatePerson(Person person, UserDTO user) {
        log.info("Update partial person for user: {}", user.toString());
        person.setName(user.getName());
        person.setMail(user.getEmail());
        person.setStatus(Status.ACTIVE);
        personCommandService.update(person);
    }

    private Person createPerson(UserDTO user) {
        log.info("Create new person for user: {}", user.toString());
        return personCommandService.add(Person.builder()
            .name(user.getName())
            .userName(user.getLogin())
            .mail(user.getEmail())
            .status(Status.ACTIVE)
            .build());
    }
}
