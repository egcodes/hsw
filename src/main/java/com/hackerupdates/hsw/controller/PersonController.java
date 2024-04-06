package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.dto.PersonDTO;
import com.hackerupdates.hsw.dto.PersonDataDTO;
import com.hackerupdates.hsw.dto.PersonProfileDTO;
import com.hackerupdates.hsw.dto.ProfileDTO;
import com.hackerupdates.hsw.dto.SettingsDTO;
import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.mapper.PersonMapper;
import com.hackerupdates.hsw.service.ProfileService;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.person.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@Api(value = "Person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final ProfileService profileService;
    private final PersonQueryService personQueryService;
    private final ConnectionQueryService connectionQueryService;
    private final PersonMapper personMapper;

    @GetMapping(value = "/get")
    @ApiOperation(value = "Get person", notes = "Get person data by id")
    public ResponseEntity<PersonDataDTO> get(@RequestHeader(Constant.PERSON_ID) Long id) {
        return ResponseEntity.ok(personService.find(id));
    }

    @PatchMapping(value = "/update")
    @ApiOperation(value = "Update person", notes = "Update person data by id")
    public boolean update(@RequestHeader(Constant.PERSON_ID) Long id, @Valid @RequestBody PersonProfileDTO personProfileDTO) {
        personProfileDTO.setId(id);
        profileService.updatePerson(personProfileDTO);
        return Boolean.TRUE;
    }

    @GetMapping(value = "/get/{userName}")
    @ApiOperation(value = "Get person", notes = "Get person data by userName")
    public ResponseEntity<PersonDataDTO> get(@RequestHeader(Constant.PERSON_ID) Long id, @PathVariable String userName) {
        return ResponseEntity.ok(profileService.findByPerson(id, userName));
    }

    @GetMapping(value = "/search/keyword={text}")
    @ApiOperation(value = "Find persons", notes = "Find person by keyword")
    public ResponseEntity<List<PersonDTO>> search(@RequestHeader(Constant.PERSON_ID) Long id, @PathVariable String text) {
        var foundPersons = new ArrayList<PersonDTO>();
        foundPersons.addAll(personMapper.toDTOs(personQueryService.findByUserNameLike(Status.ACTIVE, text)));
        foundPersons.addAll(personMapper.toDTOs(personQueryService.findByNameLike(Status.ACTIVE, text)));

        var personConnections = connectionQueryService.findConnectionIds(id);
        personConnections.add(id);
        return ResponseEntity.ok(foundPersons.stream()
            .distinct()
            .filter(p -> !personConnections.contains(p.getId()))
            .collect(Collectors.toList()));
    }

    @GetMapping(value = "/profile/{userName}")
    @ApiOperation(value = "Get person", notes = "Get person profile data by userName")
    public ResponseEntity<List<ProfileDTO>> getProfile(@RequestHeader(Constant.PERSON_ID) Long id, @PathVariable String userName) {
        return ResponseEntity.ok(profileService.findDetailsByPerson(id, userName));
    }

    @PatchMapping(value = "/settings")
    @ApiOperation(value = "Set person settings", notes = "")
    public boolean getProfile(@RequestHeader(Constant.PERSON_ID) Long id, @RequestBody SettingsDTO settings) {
        return personService.setUserSettings(id, settings);
    }

}