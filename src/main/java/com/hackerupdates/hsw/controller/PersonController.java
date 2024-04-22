package com.hackerupdates.hsw.controller;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.PersonDTO;
import com.hackerupdates.hsw.domain.dto.PersonDataDTO;
import com.hackerupdates.hsw.domain.dto.PersonProfileDTO;
import com.hackerupdates.hsw.domain.dto.ProfileDTO;
import com.hackerupdates.hsw.domain.dto.SettingsDTO;
import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.domain.mapper.PersonMapper;
import com.hackerupdates.hsw.service.ProfileService;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.person.PersonService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@Tag(name ="Person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final ProfileService profileService;
    private final PersonQueryService personQueryService;
    private final ConnectionQueryService connectionQueryService;
    private final PersonMapper personMapper;

    @GetMapping(value = "/get")
    @Operation(summary = "Get person", description = "Get person data by id")
    public ResponseEntity<PersonDataDTO> get(@RequestHeader(Constant.PERSON_ID) Long id) {
        return ResponseEntity.ok(personService.find(id));
    }

    @PatchMapping(value = "/update")
    @Operation(summary = "Update person", description = "Update person data by id")
    public boolean update(@RequestHeader(Constant.PERSON_ID) Long id, @Valid @RequestBody PersonProfileDTO personProfileDTO) {
        personProfileDTO.setId(id);
        profileService.updatePerson(personProfileDTO);
        return Boolean.TRUE;
    }

    @GetMapping(value = "/get/{userName}")
    @Operation(summary = "Get person", description = "Get person data by userName")
    public ResponseEntity<PersonDataDTO> get(@RequestHeader(Constant.PERSON_ID) Long id, @PathVariable String userName) {
        return ResponseEntity.ok(profileService.findByPerson(id, userName));
    }

    @GetMapping(value = "/search/keyword={text}")
    @Operation(summary = "Find persons", description = "Find person by keyword")
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
    @Operation(summary = "Get person", description = "Get person profile data by userName")
    public ResponseEntity<List<ProfileDTO>> getProfile(@RequestHeader(Constant.PERSON_ID) Long id, @PathVariable String userName) {
        return ResponseEntity.ok(profileService.findDetailsByPerson(id, userName));
    }

    @PatchMapping(value = "/settings")
    @Operation(summary = "Set person settings")
    public boolean getProfile(@RequestHeader(Constant.PERSON_ID) Long id, @RequestBody SettingsDTO settings) {
        return personService.setUserSettings(id, settings);
    }

}