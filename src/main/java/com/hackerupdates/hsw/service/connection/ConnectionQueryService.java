package com.hackerupdates.hsw.service.connection;

import com.hackerupdates.hsw.domain.dto.ConnectionDTO;
import com.hackerupdates.hsw.domain.dto.PersonSumDTO;
import com.hackerupdates.hsw.enums.Status;
import com.hackerupdates.hsw.domain.entity.Connection;
import com.hackerupdates.hsw.domain.repository.ConnectionRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionQueryService {

    private final ConnectionRepository connectionRepository;

    public Connection findByPersonId(Long personId, Long connectionId) {
        return connectionRepository.findByPersonIdAndConnectionId(personId, connectionId);
    }

    public List<Long> findConnectionIds(Long personId) {
        return connectionRepository.findConnectionsByPersonId(personId, Status.ACTIVE);
    }

    public int findNumOfFollowers(Long personId) {
        return connectionRepository.countByConnectionId(personId, Status.ACTIVE);
    }

    public List<PersonSumDTO> findFollowerNames(Long personId) {
        return connectionRepository.findNameByConnectionId(personId, Status.ACTIVE);
    }

    public List<PersonSumDTO> findFollowingNames(Long personId) {
        return connectionRepository.findNameByPersonId(personId, Status.ACTIVE);
    }

    public List<PersonSumDTO> findBlockedPersonNames(Long personId) {
        return connectionRepository.findNameByPersonIdAndBlocked(personId, Status.ACTIVE);
    }

    public List<PersonSumDTO> findHiddenPersonNames(Long personId) {
        return connectionRepository.findNameByPersonIdAndHidden(personId, Status.ACTIVE);
    }

    public List<ConnectionDTO> findConnections(Long personId) {
        return connectionRepository.findAllByPersonId(personId);
    }
}
