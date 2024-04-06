package com.hackerupdates.hsw.service.authentication;

import com.hackerupdates.hsw.dto.PersonDTO;
import com.hackerupdates.hsw.dto.SignInDTO;
import com.hackerupdates.hsw.dto.SignUpDTO;
import com.hackerupdates.hsw.enums.Auth;

public interface Authentication {

    PersonDTO login(Auth auth, String code);

    PersonDTO signIn(SignInDTO signInDTO);

    PersonDTO signUp(SignUpDTO signUpDTO);

    boolean logout(String code);

}
