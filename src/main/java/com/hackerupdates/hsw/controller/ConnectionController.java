package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.ConnectionDTO;
import com.hackerupdates.hsw.enums.Preference;
import com.hackerupdates.hsw.domain.mapper.ConnectionMapper;
import com.hackerupdates.hsw.service.connection.ConnectionCommandService;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
@Tag(name ="Connection")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionCommandService connectionCommandService;
    private final ConnectionQueryService connectionQueryService;
    private final ConnectionMapper connectionMapper;

    @PostMapping(value = "/addAllTo")
    @Operation(summary = "Set persons to follow for activity & shares")
    public void addAllTo(@RequestHeader(Constant.PERSON_ID) Long personId, @RequestBody List<String> userNames) {
        connectionCommandService.addAll(personId, userNames);
    }

    @GetMapping(value = "/get/{connectionId}")
    @Operation(summary = "Get connection info by personId")
    public ConnectionDTO get(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long connectionId) {
        return connectionMapper.toDTO(connectionQueryService.findByPersonId(personId, connectionId));
    }

    @PatchMapping(value = "/set/{connectionId}")
    @Operation(summary = "Set connection preference by personId")
    public boolean set(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long connectionId, @RequestBody Preference preference) {
        return connectionCommandService.setPreferenceForConnection(personId, connectionId, preference);
    }

}