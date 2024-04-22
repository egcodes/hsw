package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.service.activity.ActivityCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@Tag(name = "Activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityCommandService activityCommandService;

    @PostMapping(value = "/update")
    @Operation(summary = "Update last activity time")
    public void updateLastActivityTimeByPersonId(@RequestHeader(Constant.PERSON_ID) Long personId) {
        activityCommandService.updateLastActivityTimeByPersonId(personId);
    }

}