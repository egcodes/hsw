package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.enums.Auth;
import com.hackerswork.hsw.mapper.PersonMapper;
import com.hackerswork.hsw.service.authentication.Authentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@Api(value = "authentication")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationController {

    private final Authentication authentication;
    private final PersonMapper personMapper;

    @GetMapping(value = "/validate")
    @ApiOperation(value = "Validate token", notes = "")
    public ResponseEntity<?> validate() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/login/auth={auth}&code={code}")
    @ApiOperation(value = "Try sign up with", notes = "")
    public ResponseEntity<PersonDTO> login(@PathVariable Auth auth, @PathVariable String code) {
        return ResponseEntity.ok(personMapper.toDTO(authentication.login(auth, code)));
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "Try log out")
    public boolean logout(@RequestHeader(Constant.TOKEN) String code) {
        return authentication.logout(code);
    }

    @GetMapping(value = "createCookie/{token}")
    @ApiOperation(value = "Create a cookie", notes = "")
    public ResponseEntity<?> createCookie(@PathVariable String token) {
        var cookie = ResponseCookie.from(Constant.COOKIE_NAME, token)
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/")
            .maxAge(Constant.COOKIE_EXPIRE_TIME)
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }
}
