package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionShareDTO;
import java.util.List;

public interface ConnectionShareService {

    List<ConnectionShareDTO> findByPersonId(Long personId, String utc, int pageNumber, int pageSize);

}
