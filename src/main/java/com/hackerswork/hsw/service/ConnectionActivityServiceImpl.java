package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.enums.Activity;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionActivityServiceImpl implements ConnectionActivityService {

    private final ConnectionQueryService connectionQueryService;
    private final ActivityQueryService activityQueryService;

    @Override
    public List<ConnectionActivityDTO> findConnectionsByPerson(Long personId) {
        var connections = connectionQueryService.findConnections(personId);
        var personActivities = activityQueryService.list(connections.stream().map(
            ConnectionDTO::getConnectionId).collect(
            Collectors.toList()));

        return personActivities.stream().map(a ->
            ConnectionActivityDTO.builder()
                .personId(a.getPersonId())
                .userName(a.getUserName())
                .name(a.getName())
                .activity(getActivity(a.getLastActivityTime()))
                .pinned(connections.stream().filter(c -> c.getConnectionId().equals(a.getPersonId()))
                    .findFirst().get().isPinned())
                .build()
        ).collect(Collectors.toList());
    }

    private Activity getActivity(Long lastActivityTime) {
        return Activity.findBy(lastActivityTime);
    }
}
