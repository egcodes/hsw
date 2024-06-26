package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.PersonDataDTO;
import com.hackerupdates.hsw.domain.dto.PersonProfileDTO;
import com.hackerupdates.hsw.domain.dto.ProfileDTO;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import com.hackerupdates.hsw.service.person.PersonCommandService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.person.PersonService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final PersonService personService;
    private final PersonQueryService personQueryService;
    private final PersonCommandService personCommandService;
    private final ConnectionQueryService connectionQueryService;

    public List<ProfileDTO> findDetailsByPerson(Long id, String userName) {
        List<ProfileDTO> profiles = new ArrayList<>();

        boolean permissionForFullProfile = Boolean.FALSE;
        var person = personQueryService.findByUserName(userName);
        if (person.getId().equals(id)) {
            permissionForFullProfile = Boolean.TRUE;
        }
        id = person.getId();

        var followers = connectionQueryService.findFollowerNames(id);
        for (var follower : followers) {
            profiles.add(ProfileDTO.builder()
                .follower(follower)
                .build());
        }

        var followings = connectionQueryService.findFollowingNames(id);
        var index = 0;
        for (var following : followings) {
            if (profiles.size() > index)
                profiles.get(index).setFollowing(following);
            else
                profiles.add(ProfileDTO.builder()
                    .following(following)
                    .build());
            index++;
        }

        if (permissionForFullProfile) {
            var blockedPersons = connectionQueryService.findBlockedPersonNames(id);
            index = 0;
            for (var blocked : blockedPersons) {
                if (profiles.size() > index)
                    profiles.get(index).setBlocked(blocked);
                else
                    profiles.add(ProfileDTO.builder()
                        .blocked(blocked)
                        .build());
                index++;
            }

            var hiddenPersons = connectionQueryService.findHiddenPersonNames(id);
            index = 0;
            for (var hidden : hiddenPersons) {
                if (profiles.size() > index)
                    profiles.get(index).setHidden(hidden);
                else
                    profiles.add(ProfileDTO.builder()
                        .hidden(hidden)
                        .build());
                index++;
            }
        }

        return profiles;
    }

    public boolean updatePerson(PersonProfileDTO personProfileDTO) {
        var existPerson = personQueryService.find(personProfileDTO.getId());
        existPerson.setName(personProfileDTO.getName());
        existPerson.setAbout(personProfileDTO.getAbout());
        existPerson.setMail(personProfileDTO.getMail());
        personCommandService.update(existPerson);
        return Boolean.TRUE;
    }

    public PersonDataDTO findByPerson(Long id, String userName) {
        PersonDataDTO personData;
        var person = personQueryService.findByUserName(userName);
        if (person.getId().equals(id)) {
            personData = personService.find(id);
        } else {
            personData = personService.find(userName);
            personData.setMail(Constant.EMPTY_STRING);
        }

        return personData;
    }
}