package com.hackerswork.hsw.service.thirdParty;

import com.hackerswork.hsw.dto.GithubUserDTO;
import java.util.Optional;

public interface ThirdParty {

    Optional<GithubUserDTO> loginWithGithub(String code);

}
