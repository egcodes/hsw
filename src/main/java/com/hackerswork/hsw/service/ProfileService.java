package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.PersonDataDTO;
import com.hackerswork.hsw.dto.PersonProfileDTO;
import com.hackerswork.hsw.dto.ProfileDTO;
import java.util.List;

public interface ProfileService {

    PersonDataDTO findByPerson(Long id, String userName);

    List<ProfileDTO> findDetailsByPerson(Long id, String userName);

    boolean updatePerson(PersonProfileDTO personDTO);
}
