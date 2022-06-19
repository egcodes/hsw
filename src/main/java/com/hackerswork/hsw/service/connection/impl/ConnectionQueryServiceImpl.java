package com.hackerswork.hsw.service.connection.impl;

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
    public List<Long> findConnections(Long personId) {
        return connectionRepository.findConnectionsByPersonId(personId);
    }
}
