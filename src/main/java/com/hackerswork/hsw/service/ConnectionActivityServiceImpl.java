package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.enums.Activity;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import java.time.OffsetDateTime;
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
        var personActivities = activityQueryService.list(connections);

        return personActivities.stream().map(a ->
            ConnectionActivityDTO.builder()
                .userName(a.getUserName())
                .name(a.getName())
                .activity(getActivity(a.getLastActivityTime()))
                .build()
        ).collect(Collectors.toList());
    }

    private Activity getActivity(Long lastActivityTime) {
        var checkTime = OffsetDateTime.now().minusMinutes(3).toEpochSecond();
        if (lastActivityTime > checkTime)
            return Activity.ONLINE;
        return Activity.OFFLINE;
    }
}
