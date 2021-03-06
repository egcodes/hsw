package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.enums.Preference;
import com.hackerswork.hsw.mapper.ConnectionMapper;
import com.hackerswork.hsw.service.connection.ConnectionCommandService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "Connection")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConnectionController {

    private final ConnectionCommandService connectionCommandService;
    private final ConnectionQueryService connectionQueryService;
    private final ConnectionMapper connectionMapper;

    @PostMapping(value = "/addAllTo")
    @ApiOperation(value = "Set persons to follow for activity & shares")
    public void addAllTo(@RequestHeader(Constant.PERSON_ID) Long personId, @RequestBody List<String> userNames) {
        connectionCommandService.addAll(personId, userNames);
    }

    @GetMapping(value = "/get/{connectionId}")
    @ApiOperation(value = "Get connection info by personId")
    public ConnectionDTO get(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long connectionId) {
        return connectionMapper.toDTO(connectionQueryService.findByPersonId(personId, connectionId));
    }

    @PatchMapping(value = "/set/{connectionId}")
    @ApiOperation(value = "Set connection preference by personId")
    public boolean set(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long connectionId, @RequestBody Preference preference) {
        return connectionCommandService.setPreferenceForConnection(personId, connectionId, preference);
    }

}