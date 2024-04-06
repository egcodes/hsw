package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.dto.PersonDataDTO;
import com.hackerupdates.hsw.dto.PersonProfileDTO;
import com.hackerupdates.hsw.dto.ProfileDTO;
import java.util.List;

public interface ProfileService {

    PersonDataDTO findByPerson(Long id, String userName);

    List<ProfileDTO> findDetailsByPerson(Long id, String userName);

    boolean updatePerson(PersonProfileDTO personDTO);
}
