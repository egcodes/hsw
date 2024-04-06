package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.dto.ActivityDTO;
import com.hackerupdates.hsw.dto.ConnectionActivityDTO;
import com.hackerupdates.hsw.dto.ConnectionDTO;
import com.hackerupdates.hsw.enums.Activity;
import com.hackerupdates.hsw.service.activity.ActivityQueryService;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionActivityServiceImpl implements ConnectionActivityService {

    private final ConnectionQueryService connectionQueryService;
    private final ActivityQueryService activityQueryService;

    @Override
    public List<ConnectionActivityDTO> findOnlineConnectionsByPerson(Long personId) {
        var connections = connectionQueryService.findConnections(personId);
        var personActivities = activityQueryService.list(connections.stream().map(
            ConnectionDTO::getConnectionId).collect(
            Collectors.toList()));

        return personActivities.stream()
            .map(a -> {
                var activity = getActivity(a.getLastActivityTime());
                if (Activity.ONLINE.equals(activity))
                    return ConnectionActivityDTO.builder()
                        .personId(a.getPersonId())
                        .userName(a.getUserName())
                        .name(a.getName())
                        .activity(activity)
                        .pinned(isPinned(connections, a))
                        .build();
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<ConnectionActivityDTO> findConnectionsByPerson(Long personId) {
        var connections = connectionQueryService.findConnections(personId);
        var personActivities = activityQueryService.list(connections.stream().map(
            ConnectionDTO::getConnectionId).collect(
            Collectors.toList()));

        return personActivities.stream()
            .map(p -> ConnectionActivityDTO.builder()
                    .personId(p.getPersonId())
                    .userName(p.getUserName())
                    .name(p.getName())
                    .activity(getActivity(p.getLastActivityTime()))
                    .pinned(isPinned(connections, p))
                    .build()
            ).collect(Collectors.toList());
    }

    private boolean isPinned(List<ConnectionDTO> connections, ActivityDTO a) {
        return connections.stream().filter(c -> c.getConnectionId().equals(a.getPersonId()))
            .findFirst().orElse(ConnectionDTO.builder().build()).isPinned();
    }

    private Activity getActivity(Long lastActivityTime) {
        return Activity.findBy(lastActivityTime);
    }
}
