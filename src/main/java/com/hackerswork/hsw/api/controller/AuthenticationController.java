package com.hackerswork.hsw.api.controller;

import static com.hackerswork.hsw.constants.Constant.AUTHENTICATION_PATH;

import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.mapper.PersonMapper;
import com.hackerswork.hsw.service.authentication.Authentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AUTHENTICATION_PATH)
@Api(value = "Authentication")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationController {

    private final Authentication authentication;
    private final PersonMapper personMapper;

    @GetMapping(value = "/login/auth={auth}&code={code}")
    @ApiOperation(value = "Try sign up with", notes = "")
    public ResponseEntity<PersonDTO> login(@PathVariable Auth auth, @PathVariable String code) {
        return ResponseEntity.ok(personMapper.toDTO(authentication.login(auth, code)));
    }

}
