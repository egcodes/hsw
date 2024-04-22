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

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.password.StrongPasswordEncryptor;
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
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public PersonDTO signIn(SignInDTO signInDTO) {
        log.debug("Login attempt. UserName: {}", signInDTO.getUserName());

        Person person;
        try {
            person = personQueryService.findByUserName(signInDTO.getUserName());
        } catch (HswException e) {
            log.debug("Invalid user or password: {}", signInDTO);
            throw new HswException(ValidationRule.INVALID_USER_OR_PASSWORD);
        }
        if (!passwordEncryptor.checkPassword(signInDTO.getPassword(), person.getPassword())) {
            log.debug("Invalid user or password: {}", signInDTO);
            throw new HswException(ValidationRule.INVALID_USER_OR_PASSWORD);
        }

        log.debug("Login success. UserName: {}", person.getUserName());
        var cookieCode = generateCookieCode();
        var personDTO = personMapper.toDTO(person);
        personDTO.setCode(cookieCode);

        tokenService.set(cookieCode, String.valueOf(person.getId()));

        return personDTO;
    }

    public PersonDTO signUp(SignUpDTO signUpDTO) {
        try {
            personQueryService.findByUserName(signUpDTO.getUserName());
            log.debug("Person already exists. Redirect for login: {}", signUpDTO);
        } catch (HswException e) {
            var encodedPassword = passwordEncryptor.encryptPassword(signUpDTO.getPassword());
            var person = createPerson(UserDTO.builder()
                .login(signUpDTO.getUserName())
                .name(signUpDTO.getName())
                .email(signUpDTO.getMail())
                .password(encodedPassword)
                .build());

            var cookieCode = generateCookieCode();
            person.setStatus(Status.NEW);
            var personDTO = personMapper.toDTO(person);
            personDTO.setCode(cookieCode);

            tokenService.set(cookieCode, String.valueOf(person.getId()));

            return personDTO;
        }

        throw new HswException(ValidationRule.PERSON_ALREADY_EXISTS);
    }

    public PersonDTO signBy(Auth auth, String code) {
        log.debug("Login attempt. Auth Name: {}, Code: {}", auth.name(), code);

        if (Objects.requireNonNull(auth) == Auth.GITHUB) {
            var userPossible = authProvider.login(code);
            if (userPossible.isPresent()) {
                var user = userPossible.get();
                Person person;
                try {
                    person = personQueryService.findByUserName(user.getLogin());
                    log.debug("SignIn success by {}. User info: {}", auth.name(), person);
                } catch (Exception ignore) {
                    person = createPerson(user);
                    person.setStatus(Status.NEW);
                    log.debug("SignUp success by {}. User info: {}", auth.name(), person);
                }

                var personDTO = personMapper.toDTO(person);
                personDTO.setCode(code);
                tokenService.set(code, String.valueOf(person.getId()));
                return personDTO;
            }
        }

        log.debug("Invalid user or password: {}, {}", auth.name(), code);
        throw new HswException(ValidationRule.INVALID_USER_OR_PASSWORD);
    }

    public boolean logout(String token) {
        log.debug("Logout for token: {}", token);
        tokenService.remove(token);
        return Boolean.TRUE;
    }

    private String generateCookieCode() {
        return String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }

    private Person createPerson(UserDTO user) {
        log.info("Create new person for user: {}", user);
        return personCommandService.add(Person.builder()
            .name(nonNull(user.getName()) ? user.getName() : user.getLogin())
            .userName(user.getLogin())
            .mail(user.getEmail())
            .status(Status.ACTIVE)
            .password(user.getPassword())
            .build());
    }
}
