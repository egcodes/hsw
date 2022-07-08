package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ProfileDTO;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileServiceImpl implements ProfileService {

    private final ConnectionQueryService connectionQueryService;

    @Override
    public List<ProfileDTO> findByPerson(Long id) {
        List<ProfileDTO> profiles = new ArrayList<>();

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
        return profiles;
    }
}