package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.service.activity.ActivityCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@Api(value = "Activity")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ActivityController {

    private final ActivityCommandService activityCommandService;

    @PostMapping(value = "/update")
    @ApiOperation(value = "Update last activity time")
    public void updateLastActivityTimeByPersonId(@RequestHeader("personId") Long personId) {
        activityCommandService.updateLastActivityTimeByPersonId(personId);
    }

}