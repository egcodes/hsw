package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.dto.PersonProfileDTO;
import com.hackerswork.hsw.dto.ProfileDTO;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonCommandService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.person.PersonService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileServiceImpl implements ProfileService {

    private final PersonService personService;
    private final PersonQueryService personQueryService;
    private final PersonCommandService personCommandService;
    private final ConnectionQueryService connectionQueryService;

    @Override
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

    @Override
    public boolean updatePerson(PersonProfileDTO personProfileDTO) {
        var existPerson = personQueryService.find(personProfileDTO.getId());
        existPerson.setName(personProfileDTO.getName());
        existPerson.setAbout(personProfileDTO.getAbout());
        existPerson.setMail(personProfileDTO.getMail());
        personCommandService.update(existPerson);
        return Boolean.TRUE;
    }

    @Override
    public PersonDataDTO findByPerson(Long id, String userName) {
        PersonDataDTO personData;
        var person = personQueryService.findByUserName(userName);
        if (person.getId().equals(id)) {
            id = person.getId();
            personData = personService.find(id);
        } else {
            personData = personService.find(userName);
            personData.setMail("");
        }

        return personData;
    }
}