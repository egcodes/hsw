package com.hackerswork.hsw.service.connection;

import com.hackerswork.hsw.persistence.entity.Connection;
import java.util.List;

public interface ConnectionQueryService {

    List<Long> findConnections(Long personId);
}
