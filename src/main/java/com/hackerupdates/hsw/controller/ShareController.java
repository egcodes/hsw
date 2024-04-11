package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.ShareDTO;
import com.hackerupdates.hsw.domain.mapper.ShareMapper;
import com.hackerupdates.hsw.service.share.ShareCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share")
@Api(value = "Share")
@RequiredArgsConstructor
@Slf4j
public class ShareController {

    private final ShareCommandService shareCommandService;
    private final ShareMapper shareMapper;

    @PostMapping(value = "/add")
    @ApiOperation(value = "New post")
    public ResponseEntity<ShareDTO> add(@RequestHeader(Constant.PERSON_ID) Long personId, @Valid @RequestBody ShareDTO share) {
        return ResponseEntity.ok(shareMapper.toDTO(shareCommandService.add(personId, shareMapper.toEntity(share))));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long id) {
        shareCommandService.delete(personId, id);
    }

}