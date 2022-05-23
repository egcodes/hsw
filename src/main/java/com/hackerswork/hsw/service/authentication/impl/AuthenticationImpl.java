package com.hackerswork.hsw.service.authentication.impl;

import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.authentication.AuthProvider;
import com.hackerswork.hsw.service.authentication.Authentication;
import com.hackerswork.hsw.service.person.PersonCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationImpl implements Authentication {

    private final AuthProvider authProvider;
    private final PersonCommandService personCommandService;

    @Override
    public Person login(Auth auth, String code) {
        switch (auth) {
            case GITHUB:
                var userPossible = authProvider.login(code);
                if (userPossible.isPresent()) {
                    var user = userPossible.get();
                    return personCommandService.add(Person.builder()
                        .name(user.getName())
                        .userName(user.getLogin())
                        .mail(user.getEmail())
                        .build());
                }
                break;
            case LINKEDIN:
            case TWITTER:
                break;
        }

        return null;
    }
}
