package com.hackerswork.hsw.service.authentication.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.hackerswork.hsw.constants.Constant.GithubRequestHeader;
import com.hackerswork.hsw.dto.UserDTO;
import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.properties.AuthProviderProperties;
import com.hackerswork.hsw.service.ResourceService;
import com.hackerswork.hsw.service.filter.AuthenticationFilter;
import com.hackerswork.hsw.service.authentication.AuthProvider;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GithubAuthProviderImpl implements AuthProvider {

    private final AuthenticationFilter securityFilter;
    private final ResourceService resourceService;
    private final AuthProviderProperties authProviderProperties;
    private final RestTemplate restTemplate;

    private static final String tokenName = "access_token";
    private static final String headerKeyForAuth = "Authorization";
    private static final String tokenPrefix = "token ";

    @Override
    public Optional<UserDTO> login(String code) {
        var nodeFactory = new JsonNodeFactory(false);
        var json = nodeFactory.objectNode();
        json.put(GithubRequestHeader.CLIENT_ID, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_ID));
        json.put(GithubRequestHeader.CLIENT_SECRET, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_SECRET));
        json.put(GithubRequestHeader.CODE, code);
        json.put(GithubRequestHeader.REDIRECT_URI, authProviderProperties.getGithub().getRedirectUrl());

        var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        var result = restTemplate.exchange(authProviderProperties.getGithub().getLoginUrl(),
            HttpMethod.POST, new HttpEntity<>(json, headers), JsonNode.class);
        if (HttpStatus.OK.equals(result.getStatusCode())) {
            var response = result.getBody();
            var accessToken = Optional.ofNullable(response.get(tokenName));

            if (accessToken.isPresent()) {
                headers.setAccessControlAllowOrigin("*");
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                headers.set(headerKeyForAuth, tokenPrefix + accessToken.get().asText());
                var user = restTemplate.exchange(authProviderProperties.getGithub().getUserUrl(),
                    HttpMethod.GET, new HttpEntity<>(null, headers), UserDTO.class);
                var githubUserDTO = user.getBody();
                securityFilter.getCache().put(githubUserDTO.getLogin(), code);
                return Optional.ofNullable(githubUserDTO);
            }
        }

        throw new HswException(ValidationRule.COULD_NOT_SIGN_IN);
    }

}