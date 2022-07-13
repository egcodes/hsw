package com.hackerswork.hsw.api.controller;

import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.dto.ProfileDTO;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.mapper.PersonMapper;
import com.hackerswork.hsw.service.ProfileService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.person.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
@Api(value = "Person")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PersonController {

    private final PersonCommandService personCommandService;
    private final PersonService personService;
    private final ProfileService profileService;
    private final PersonQueryService personQueryService;
    private final ConnectionQueryService connectionQueryService;
    private final PersonMapper personMapper;

    @PostMapping(value = "/add")
    @ApiOperation(value = "Add person", notes = "Adding new person")
    public ResponseEntity<PersonDTO> add(@Valid @RequestBody PersonDTO dto) {
        return ResponseEntity.ok(personMapper.toDTO(personCommandService.add(personMapper.toEntity(dto))));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get person", notes = "Get person data by id")
    public ResponseEntity<PersonDataDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(personService.find(id));
    }

    @GetMapping(value = "/search/keyword={text}")
    @ApiOperation(value = "Find persons", notes = "Find person by keyword")
    public ResponseEntity<List<PersonDTO>> get(@RequestHeader("personId") Long personId, @PathVariable String text) {
        var foundPersons = new ArrayList<PersonDTO>();
        foundPersons.addAll(personMapper.toDTOs(personQueryService.findByUserNameLike(Status.ACTIVE, text)));
        foundPersons.addAll(personMapper.toDTOs(personQueryService.findByNameLike(Status.ACTIVE, text)));

        var personConnections = connectionQueryService.findConnectionIds(personId);
        personConnections.add(personId);
        return ResponseEntity.ok(foundPersons.stream()
            .filter(p -> !personConnections.contains(p.getId()))
            .collect(Collectors.toList()));
    }

    @GetMapping(value = "/profile/{id}")
    @ApiOperation(value = "Get person", notes = "Get person profile data by id")
    public ResponseEntity<List<ProfileDTO>> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.findByPerson(id));
    }
}