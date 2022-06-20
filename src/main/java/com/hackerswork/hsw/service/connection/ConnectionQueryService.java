package com.hackerswork.hsw.service.connection;

import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.persistence.entity.Connection;
import java.util.List;

public interface ConnectionQueryService {

    Connection findByPersonId(Long personId, Long connectionId);

    List<Long> findConnectionIds(Long personId);

    List<ConnectionDTO> findConnections(Long personId);

}
