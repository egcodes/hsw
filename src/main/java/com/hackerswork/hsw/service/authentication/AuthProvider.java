package com.hackerswork.hsw.service.authentication;

import com.hackerswork.hsw.dto.UserDTO;
import java.util.Optional;

public interface AuthProvider {

    Optional<UserDTO> login(String code);

}
