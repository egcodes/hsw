package com.hackerswork.hsw.service.authentication;

import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.dto.SignUpDTO;
import com.hackerswork.hsw.enums.Auth;

public interface Authentication {

    PersonDTO login(Auth auth, String code);

    PersonDTO signIn(SignUpDTO signUpDTO);

    PersonDTO signUp(SignUpDTO signUpDTO);

    boolean logout(String code);

}
