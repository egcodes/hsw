package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionShareDTO;
import com.hackerswork.hsw.persistence.entity.Connection;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonQueryService;
import com.hackerswork.hsw.service.share.ShareQueryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionShareServiceImpl implements ConnectionShareService {

    private final ConnectionQueryService connectionQueryService;
    private final ShareQueryService shareQueryService;
    private final PersonQueryService personQueryService;

    @Override
    public List<ConnectionShareDTO> findByPersonId(Long personId) {
        var shareList = new ArrayList<ConnectionShareDTO>();
        var connectionList = connectionQueryService.list(personId);

        connectionList.add(Connection.builder().connectionId(personId).build());

        for (var connection : connectionList) {
            var shares = shareQueryService.findByPersonId(connection.getConnectionId());
            var person = personQueryService.findPerson(connection.getConnectionId()).get();

            for (var share : shares) {
                shareList.add(ConnectionShareDTO.builder()
                    .name(person.getName())
                    .userName(person.getUserName())
                    .share(share)
                    .build());
            }
        }

        shareList.sort((a, b) -> a.getShare().getCreatedTime().isAfter(b.getShare().getCreatedTime()) ? -1 : 0);

        return shareList;
    }
}
