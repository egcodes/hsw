package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.dto.ConnectionShareDTO;
import com.hackerswork.hsw.service.ConnectionShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectionShare")
@Api(value = "ConnectionShare")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConnectionShareController {

    private final ConnectionShareService connectionShareService;

    @PostMapping(value = "/get/{shareId}")
    @ApiOperation(value = "Get person-connection share by id that the person connected")
    public ResponseEntity<ConnectionShareDTO> get(@PathVariable Long shareId, @RequestParam String utc) {
        return ResponseEntity.ok(connectionShareService.findByShareId(shareId, utc));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "Get person-connection share that the person connected")
    public ResponseEntity<List<ConnectionShareDTO>> list(@RequestHeader("personId") Long personId, @RequestParam String utc,
        @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(connectionShareService.findByPersonId(personId, utc, pageNumber, pageSize));
    }

    @PostMapping(value = "/listFrom")
    @ApiOperation(value = "Get person-connection share by offset that the person connected")
    public ResponseEntity<List<ConnectionShareDTO>> listFrom(@RequestHeader("personId") Long personId,
        @RequestParam Long offset, @RequestParam String utc) {
        return ResponseEntity.ok(connectionShareService.findByOffsetAndPersonId(personId, offset, utc));
    }
}