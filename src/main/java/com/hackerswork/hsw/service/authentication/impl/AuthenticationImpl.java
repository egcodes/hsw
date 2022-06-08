package com.hackerswork.hsw.service.authentication.impl;

import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.authentication.AuthProvider;
import com.hackerswork.hsw.service.authentication.Authentication;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
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

    @Override
    public Person login(Auth auth, String code) {
        switch (auth) {
            case GITHUB:
                var userPossible = authProvider.login(code);
                if (userPossible.isPresent()) {
                    var user = userPossible.get();

                    var personPossible = personQueryService.findPersonByUserName(user.getLogin());
                    if (personPossible.isEmpty() || Status.PARTIAL.equals(personPossible.get().getStatus())) {
                        var newPerson = personCommandService.add(Person.builder()
                            .name(user.getName())
                            .userName(user.getLogin())
                            .mail(user.getEmail())
                            .status(Status.ACTIVE)
                            .build());
                        newPerson.setStatus(Status.NEW);
                        return newPerson;
                    }

                    return personPossible.get();
                }
                break;
            case LINKEDIN:
            case TWITTER:
                break;
        }

        return null;
    }
}
