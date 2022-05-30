package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import com.hackerswork.hsw.enums.Activity;
import com.hackerswork.hsw.service.activity.ActivityQueryService;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionActivityServiceImpl implements ConnectionActivityService {

    private final PersonQueryService personQueryService;
    private final ConnectionQueryService connectionQueryService;
    private final ActivityQueryService activityQueryService;

    @Override
    public List<ConnectionActivityDTO> findConnectionsByPerson(Long personId) {
        var resultLs = new ArrayList<ConnectionActivityDTO>();

        var connections = connectionQueryService.list(personId);
        for (var con : connections) {
            var person = personQueryService.findPerson(con.getConnectionId()).get();
            var activity = activityQueryService.isOnline(con.getConnectionId())
                ? Activity.ONLINE : Activity.OFFLINE;
            resultLs.add(ConnectionActivityDTO.builder()
                .name(person.getName())
                .userName(person.getUserName())
                .activity(activity)
                .pinned(con.isPinned())
                .build());
        }

        resultLs.sort((p1, p2) -> p1.getActivity().ordinal() > p2.getActivity().ordinal() ? -1 : 1);
        return resultLs;
    }
}
