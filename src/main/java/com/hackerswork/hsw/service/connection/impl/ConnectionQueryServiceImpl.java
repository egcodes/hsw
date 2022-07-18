package com.hackerswork.hsw.service.connection.impl;

import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.dto.PersonSumDTO;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.persistence.entity.Connection;
import com.hackerswork.hsw.persistence.repository.ConnectionRepository;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionQueryServiceImpl implements ConnectionQueryService {

    private final ConnectionRepository connectionRepository;

    @Override
    public Connection findByPersonId(Long personId, Long connectionId) {
        return connectionRepository.findByPersonIdAndConnectionId(personId, connectionId);
    }

    @Override
    public List<Long> findConnectionIds(Long personId) {
        return connectionRepository.findConnectionsByPersonId(personId, Status.ACTIVE);
    }

    @Override
    public int findNumOfFollowers(Long personId) {
        return connectionRepository.countByConnectionId(personId);
    }

    @Override
    public List<PersonSumDTO> findFollowerNames(Long personId) {
        return connectionRepository.findNameByConnectionId(personId, Status.ACTIVE);
    }

    @Override
    public List<PersonSumDTO> findFollowingNames(Long personId) {
        return connectionRepository.findNameByPersonId(personId, Status.ACTIVE);
    }

    @Override
    public List<PersonSumDTO> findBlockedPersonNames(Long personId) {
        return connectionRepository.findNameByPersonIdAndBlocked(personId, Status.ACTIVE);
    }

    @Override
    public List<PersonSumDTO> findHiddenPersonNames(Long personId) {
        return connectionRepository.findNameByPersonIdAndHidden(personId, Status.ACTIVE);
    }

    @Override
    public List<ConnectionDTO> findConnections(Long personId) {
        return connectionRepository.findAllByPersonId(personId);
    }
}
