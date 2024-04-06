package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.dto.ConnectionActivityDTO;
import java.util.List;

public interface ConnectionActivityService {

    List<ConnectionActivityDTO> findConnectionsByPerson(Long personId);

    List<ConnectionActivityDTO> findOnlineConnectionsByPerson(Long personId);

}
