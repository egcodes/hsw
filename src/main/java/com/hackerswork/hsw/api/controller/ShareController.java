package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.mapper.ShareMapper;
import com.hackerswork.hsw.service.share.ShareCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ShareController {

    private final ShareCommandService shareCommandService;
    private final ShareMapper shareMapper;

    @PostMapping(value = "/add")
    @ApiOperation(value = "New post")
    public ResponseEntity<ShareDTO> add(@RequestHeader(Constant.PERSON_ID) Long personId, @RequestBody ShareDTO share) {
        return ResponseEntity.ok(shareMapper.toDTO(shareCommandService.add(personId, shareMapper.toEntity(share))));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@RequestHeader(Constant.PERSON_ID) Long personId, @PathVariable Long id) {
        shareCommandService.delete(personId, id);
    }

}