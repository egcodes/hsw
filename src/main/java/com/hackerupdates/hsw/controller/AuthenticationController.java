package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.PersonDTO;
import com.hackerupdates.hsw.domain.dto.SignInDTO;
import com.hackerupdates.hsw.domain.dto.SignUpDTO;
import com.hackerupdates.hsw.enums.Auth;
import com.hackerupdates.hsw.service.authentication.Authentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@Tag(name ="Authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final Authentication authentication;

    @GetMapping(value = "/validate")
    @Operation(summary = "Validate token")
    public ResponseEntity<?> validate() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/signIn")
    @Operation(summary = "Try sign in with")
    public ResponseEntity<PersonDTO> signIn(@Valid @RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authentication.signIn(signInDTO));
    }

    @PostMapping(value = "/signUp")
    @Operation(summary = "Try sign up with")
    public ResponseEntity<PersonDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(authentication.signUp(signUpDTO));
    }

    @GetMapping(value = "/sign/auth={auth}&code={code}")
    @Operation(summary = "Try sign in with")
    public ResponseEntity<PersonDTO> signBy(@PathVariable Auth auth, @PathVariable String code) {
        return ResponseEntity.ok(authentication.signBy(auth, code));
    }

    @GetMapping(value = "/logout")
    @Operation(summary = "Try log out")
    public boolean logout(@RequestHeader(Constant.TOKEN) String code) {
        return authentication.logout(code);
    }

    @GetMapping(value = "createCookie/{token}")
    @Operation(summary = "Create a cookie")
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
