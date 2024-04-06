package com.hackerswork.hsw.controller;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.service.ConnectionActivityService;
import com.hackerswork.hsw.service.activity.ActivityCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectionActivity")
@Api(value = "ConnectionActivity")
@RequiredArgsConstructor
@Slf4j
public class ConnectionActivityController {

    private final ActivityCommandService activityCommandService;
    private final ConnectionActivityService connectionActivityService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "Get person-connection activity info that the person connected")
    public ResponseEntity<List<ConnectionActivityDTO>> list(@RequestHeader(Constant.PERSON_ID) Long personId) {
        return ResponseEntity.ok(connectionActivityService.findConnectionsByPerson(personId));
    }

    @GetMapping(value = "/onlineList")
    @ApiOperation(value = "Get only online person-connection that the person connected")
    public ResponseEntity<List<ConnectionActivityDTO>> onlineList(@RequestHeader(Constant.PERSON_ID) Long personId) {
        activityCommandService.updateLastActivityTimeByPersonId(personId);
        return ResponseEntity.ok(connectionActivityService.findOnlineConnectionsByPerson(personId));
    }
}