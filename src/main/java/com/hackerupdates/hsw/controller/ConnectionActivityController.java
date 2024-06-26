package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.ConnectionActivityDTO;
import com.hackerupdates.hsw.service.ConnectionActivityService;
import com.hackerupdates.hsw.service.activity.ActivityCommandService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectionActivity")
@Tag(name ="ConnectionActivity")
@RequiredArgsConstructor
@Slf4j
public class ConnectionActivityController {

    private final ActivityCommandService activityCommandService;
    private final ConnectionActivityService connectionActivityService;

    @GetMapping(value = "/list")
    @Operation(summary = "Get person-connection activity info that the person connected")
    public ResponseEntity<List<ConnectionActivityDTO>> list(@RequestHeader(Constant.PERSON_ID) Long personId) {
        return ResponseEntity.ok(connectionActivityService.findConnectionsByPerson(personId));
    }

    @GetMapping(value = "/onlineList")
    @Operation(summary = "Get only online person-connection that the person connected")
    public ResponseEntity<List<ConnectionActivityDTO>> onlineList(@RequestHeader(Constant.PERSON_ID) Long personId) {
        activityCommandService.updateLastActivityTimeByPersonId(personId);
        return ResponseEntity.ok(connectionActivityService.findOnlineConnectionsByPerson(personId));
    }
}