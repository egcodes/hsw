package com.hackerupdates.hsw.service.authentication;

import com.hackerupdates.hsw.dto.UserDTO;
import java.util.Optional;

public interface AuthProvider {

    Optional<UserDTO> login(String code);

}
