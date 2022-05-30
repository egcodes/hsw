package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ConnectionActivityDTO;
import java.util.List;

public interface ConnectionActivityService {

    List<ConnectionActivityDTO> findConnectionsByPerson(Long personId);

}
