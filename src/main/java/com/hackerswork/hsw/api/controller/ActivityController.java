package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.service.activity.ActivityCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/person")
@Api(value = "Person")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ActivityController {

    private final ActivityCommandService activityCommandService;

    @PutMapping(value = "/update/{personId}")
    @ApiOperation(value = "Update last activity time")
    public void updateLastActivityTimeByPersonId(@PathVariable Long personId) {
        activityCommandService.updateLastActivityTimeByPersonId(personId);
    }

}