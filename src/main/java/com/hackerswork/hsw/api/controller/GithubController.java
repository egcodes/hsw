package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.service.thirdParty.ThirdParty;
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
@RequestMapping("/client/github")
@Api(value = "GitHub")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GithubController {

    private final ThirdParty thirdParty;

    @GetMapping(value = "/getAccessToken/{code}")
    @ApiOperation(value = "Get access token", notes = "Getting access token, scope, token_type from GitHub")
    public ResponseEntity<String> getAccessToken(@PathVariable String code) {
        return ResponseEntity.ok(thirdParty.getAccessToken(code));
    }

}
