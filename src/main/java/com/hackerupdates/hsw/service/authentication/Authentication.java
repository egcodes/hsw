package com.hackerupdates.hsw.service.authentication;

import static java.util.Objects.nonNull;

import com.hackerupdates.hsw.domain.dto.PersonDTO;
import com.hackerupdates.hsw.domain.dto.SignInDTO;
import com.hackerupdates.hsw.domain.dto.SignUpDTO;
import com.hackerupdates.hsw.domain.dto.UserDTO;
import com.hackerupdates.hsw.enums.Auth;
import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.domain.mapper.PersonMapper;
import com.hackerupdates.hsw.domain.entity.Person;
import com.hackerupdates.hsw.service.person.PersonCommandService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.security.TokenService;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Authentication {

    private final GithubAuthProvider authProvider;
    private final PersonCommandService personCommandService;
    private final PersonQueryService personQueryService;
    private final TokenService tokenService;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;


    public PersonDTO login(Auth auth, String code) {
        Person personInfo = null;
        var isRegistration = Boolean.FALSE;

        if (Objects.requireNonNull(auth) == Auth.GITHUB) {
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
        }

        tokenService.set(personInfo.getId(), personInfo.getUserName(), code);
        if (isRegistration)
            personInfo.setStatus(Status.NEW);

        var personDTO = personMapper.toDTO(personInfo);
        personDTO.setCode(code);

        return personDTO;
    }

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
