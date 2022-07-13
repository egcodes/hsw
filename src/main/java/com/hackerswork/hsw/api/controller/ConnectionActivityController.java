package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.service.ConnectionActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectionActivity")
@Api(value = "ConnectionActivity")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConnectionActivityController {

    private final ConnectionActivityService connectionActivityService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "Get person-connection activity info that the person connected")
    public ResponseEntity<List<ConnectionActivityDTO>> list(@RequestHeader("personId") Long personId) {
        return ResponseEntity.ok(connectionActivityService.findConnectionsByPerson(personId));
    }

}