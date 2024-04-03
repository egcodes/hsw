package com.hackerswork.hsw.service.authentication.impl;

import static java.util.Objects.nonNull;

import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.dto.SignInDTO;
import com.hackerswork.hsw.dto.SignUpDTO;
import com.hackerswork.hsw.dto.UserDTO;
import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.mapper.PersonMapper;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.authentication.AuthProvider;
import com.hackerswork.hsw.service.authentication.Authentication;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.security.TokenService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationImpl implements Authentication {

    private final AuthProvider authProvider;
    private final PersonCommandService personCommandService;
    private final PersonQueryService personQueryService;
    private final TokenService tokenService;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public PersonDTO login(Auth auth, String code) {
        Person personInfo = null;
        var isRegistration = Boolean.FALSE;

        switch (auth) {
            case GITHUB:
                var userPossible = authProvider.login(code);
                if (userPossible.isPresent()) {
                    log.debug("Login success. GitHub user info: {}", userPossible);

                    var user = userPossible.get();
                    try {
                        personInfo = personQueryService.findByUserName(user.getLogin());
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

        var personDTO = personMapper.toDTO(personInfo);
        personDTO.setCode(code);

        return personDTO;
    }

    @Override
    public PersonDTO signIn(SignInDTO signInDTO) {
        Person personInfo;
        try {
            personInfo = personQueryService.findByUserName(signInDTO.getUserName());
        } catch (HswException e) {
            throw new HswException(ValidationRule.INVALID_USER_OR_PASSWORD);
        }
        if (!passwordEncoder.matches(signInDTO.getPassword(), personInfo.getPassword())) {
            throw new HswException(ValidationRule.INVALID_USER_OR_PASSWORD);
        }

        var cookieCode = String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        tokenService.set(personInfo.getId(), personInfo.getUserName(), cookieCode);

        var personDTO = personMapper.toDTO(personInfo);
        personDTO.setCode(cookieCode);

        return personDTO;
    }

    @Override
    public PersonDTO signUp(SignUpDTO signUpDTO) {
        try {
            personQueryService.findByUserName(signUpDTO.getUserName());
        } catch (HswException e) {
            var encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
            var personInfo = createPerson(UserDTO.builder()
                .login(signUpDTO.getUserName())
                .email(signUpDTO.getMail())
                .password(encodedPassword)
                .build());

            var cookieCode = String.valueOf(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            tokenService.set(personInfo.getId(), personInfo.getUserName(), cookieCode);

            personInfo.setStatus(Status.NEW);
            var personDTO = personMapper.toDTO(personInfo);
            personDTO.setCode(cookieCode);

            return personDTO;
        }

        throw new HswException(ValidationRule.PERSON_ALREADY_EXISTS);
    }

    @Override
    public boolean logout(String code) {
        return tokenService.remove(code);
    }

    private Person createPerson(UserDTO user) {
        log.info("Create new person for user: {}", user.toString());
        return personCommandService.add(Person.builder()
            .name(nonNull(user.getName()) ? user.getName() : user.getLogin())
            .userName(user.getLogin())
            .mail(user.getEmail())
            .status(Status.ACTIVE)
            .password(user.getPassword())
            .build());
    }
}
