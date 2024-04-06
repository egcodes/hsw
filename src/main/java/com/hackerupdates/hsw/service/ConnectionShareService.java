package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.dto.ConnectionShareDTO;
import java.util.List;

public interface ConnectionShareService {

    List<ConnectionShareDTO> findByPersonId(Long personId, int pageNumber, int pageSize);

    List<ConnectionShareDTO> findByOffsetAndPersonId(Long personId, Long offset);

    ConnectionShareDTO findByShareId(Long shareId);

}
