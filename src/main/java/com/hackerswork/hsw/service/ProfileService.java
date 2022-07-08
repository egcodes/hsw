package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.ProfileDTO;
import java.util.List;

public interface ProfileService {

    List<ProfileDTO> findByPerson(Long id);

}
