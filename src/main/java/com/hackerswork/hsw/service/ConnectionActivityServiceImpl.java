package com.hackerswork.hsw.service;

import static com.hackerswork.hsw.constants.Constant.DURATION_FOR_ONLINE;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.enums.Activity;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import java.time.Instant;
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
        var checkTime = Instant.now().minusMillis(DURATION_FOR_ONLINE).toEpochMilli();
        if (lastActivityTime > checkTime)
            return Activity.ONLINE;
        return Activity.OFFLINE;
    }
}
