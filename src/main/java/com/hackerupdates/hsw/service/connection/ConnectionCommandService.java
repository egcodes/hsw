package com.hackerupdates.hsw.service.connection;

import com.hackerupdates.hsw.enums.Preference;
import com.hackerupdates.hsw.domain.entity.Connection;
import com.hackerupdates.hsw.domain.repository.ConnectionRepository;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ConnectionCommandService {

    private final ConnectionRepository connectionRepository;
    private final PersonQueryService personQueryService;

    public void addAll(Long personId, List<String> userNames) {
        var persons = personQueryService.findAllByUserName(userNames);

        var connections = new ArrayList<Connection>();
        for (var userName : userNames) {
            var personPossible = persons.stream()
                .filter(p -> p.getUserName().equals(userName))
                .findFirst();

            personPossible.ifPresent(p ->
                connections.add(Connection.builder()
                    .personId(personId)
                    .connectionId(p.getId())
                    .build()));
        }
        connectionRepository.saveAll(connections);
    }

    public boolean setPreferenceForConnection(Long personId, Long connectionId, Preference preference) {
        var connection = connectionRepository.findByPersonIdAndConnectionId(personId, connectionId);
        connection = preference.setConnectionPreferenceForPerson(connection);
        var result = connectionRepository.save(connection);
        return result.getConnectionId().equals(connectionId);
    }
}
