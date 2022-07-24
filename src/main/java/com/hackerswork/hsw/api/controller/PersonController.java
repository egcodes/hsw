package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.dto.PersonProfileDTO;
import com.hackerswork.hsw.dto.ProfileDTO;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.mapper.PersonMapper;
import com.hackerswork.hsw.service.ProfileService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.person.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@Api(value = "Person")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

}