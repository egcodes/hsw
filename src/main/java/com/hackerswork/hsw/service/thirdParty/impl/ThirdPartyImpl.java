package com.hackerswork.hsw.service.thirdParty.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.hackerswork.hsw.constants.Constant.GithubRequestHeader;
import com.hackerswork.hsw.dto.GithubUserDTO;
import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.properties.Properties;
import com.hackerswork.hsw.service.ResourceService;
import com.hackerswork.hsw.service.AuthenticationFilter;
import com.hackerswork.hsw.service.thirdParty.ThirdParty;
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
public class ThirdPartyImpl implements ThirdParty {

    private final AuthenticationFilter securityFilter;
    private final ResourceService resourceService;
    private final Properties properties;
    private final RestTemplate restTemplate;

    @Override
    public Optional<GithubUserDTO> loginWithGithub(String code) {
        var nodeFactory = new JsonNodeFactory(false);
        var json = nodeFactory.objectNode();
        json.put(GithubRequestHeader.CLIENT_ID, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_ID));
        json.put(GithubRequestHeader.CLIENT_SECRET, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_SECRET));
        json.put(GithubRequestHeader.CODE, code);
        json.put(GithubRequestHeader.REDIRECT_URI, properties.getGithubRedirectUri());

        var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        var result = restTemplate.exchange(properties.getGithubLoginUrl(), HttpMethod.POST, new HttpEntity<>(json, headers), JsonNode.class);
        if (HttpStatus.OK.equals(result.getStatusCode())) {
            var response = result.getBody();
            var accessToken = Optional.ofNullable(response.get("access_token"));

            if (accessToken.isPresent()) {
                headers.setAccessControlAllowOrigin("*");
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                headers.set("Authorization", "token " + accessToken.get().asText());
                var user = restTemplate.exchange(properties.getGithubUserUrl(), HttpMethod.GET, new HttpEntity<>(null, headers), GithubUserDTO.class);
                var githubUserDTO = user.getBody();
                securityFilter.getCache().put(githubUserDTO.getLogin(), code);
                return Optional.ofNullable(githubUserDTO);
            }
        }

        throw new HswException(ValidationRule.PERSON_NOT_FOUND);
    }

}