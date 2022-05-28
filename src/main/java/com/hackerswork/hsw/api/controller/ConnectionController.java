package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.service.connection.ConnectionCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
@Api(value = "Connection")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConnectionController {

    private final ConnectionCommandService connectionCommandService;

    @PostMapping(value = "/addAllTo/{personId}")
    @ApiOperation(value = "Set persons to follow for activity & shares")
    public void addAllTo(@PathVariable Long personId, @RequestBody List<String> userNames) {
        connectionCommandService.addAll(personId, userNames);
    }

}