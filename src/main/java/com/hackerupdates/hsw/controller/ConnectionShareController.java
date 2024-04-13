package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.ConnectionShareDTO;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.service.ConnectionShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectionShare")
@Api(value = "ConnectionShare")
@RequiredArgsConstructor
@Slf4j
public class ConnectionShareController {

    private final ConnectionShareService connectionShareService;

    @PostMapping(value = "/listByPersonId")
    @ApiOperation(value = "Get person-connection shares that the person connected")
    public ResponseEntity<List<ConnectionShareDTO>> list(@RequestHeader(Constant.PERSON_ID) Long personId,
        @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(connectionShareService.findByPersonId(personId, pageNumber, pageSize));
    }

    @PostMapping(value = "/listByPersonIdFrom")
    @ApiOperation(value = "Get person-connection shares by offset that the person connected")
    public ResponseEntity<List<ConnectionShareDTO>> listFrom(@RequestHeader(Constant.PERSON_ID) Long personId, @RequestParam Long offset) {
        return ResponseEntity.ok(connectionShareService.findByOffsetAndPersonId(personId, offset));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "Get all shares")
    public ResponseEntity<List<ConnectionShareDTO>> list(@RequestParam int pageNumber, @RequestParam int pageSize) {
        int PAGE_SIZE_LIMIT = 40;
        if (pageSize > PAGE_SIZE_LIMIT)
            throw new HswException(ValidationRule.UNAUTHORIZED_ACCESS);
        return ResponseEntity.ok(connectionShareService.findAllShares(pageNumber, pageSize));
    }

    @PostMapping(value = "/listFrom")
    @ApiOperation(value = "Get all shares by offset")
    public ResponseEntity<List<ConnectionShareDTO>> listFrom(@RequestParam Long offset) {
        return ResponseEntity.ok(connectionShareService.findByOffset(offset));
    }


}