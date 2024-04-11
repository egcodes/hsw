package com.hackerupdates.hsw.service.authentication;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hackerupdates.hsw.constants.Constant.GithubRequestHeader;
import com.hackerupdates.hsw.domain.dto.UserDTO;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.config.properties.AuthProviderProperties;
import com.hackerupdates.hsw.service.ResourceService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GithubAuthProvider {

    private final ResourceService resourceService;
    private final AuthProviderProperties authProviderProperties;
    private final RestTemplate restTemplate;

    private final String tokenName = "access_token";
    private final String headerKeyForAuth = "Authorization";
    private final String tokenPrefix = "token ";
    private final String allowOrigin = "*";

    public Optional<UserDTO> login(String code) {
        var body = createBody(code);
        var headers = createHeaders();
        var respForAccessToken = getAccessToken(body, headers);

        if (HttpStatus.OK.equals(respForAccessToken.getStatusCode())) {
            var respBody = respForAccessToken.getBody();
            var accessToken = respBody.get(tokenName);

            if (nonNull(accessToken)) {
                addTokenToHeaders(headers, accessToken);
                var respForUser = getUserByAccessToken(headers);

                if (HttpStatus.OK.equals(respForUser.getStatusCode())) {
                    var githubUserDTO = respForUser.getBody();
                    return Optional.of(githubUserDTO);
                }
            }
        }

        throw new HswException(ValidationRule.COULD_NOT_SIGN_IN);
    }

    private ObjectNode createBody(String code) {
        var nodeFactory = new JsonNodeFactory(false);
        var json = nodeFactory.objectNode();
        json.put(GithubRequestHeader.CLIENT_ID, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_ID));
        json.put(GithubRequestHeader.CLIENT_SECRET, resourceService.getAuthList().get(GithubRequestHeader.CLIENT_SECRET));
        json.put(GithubRequestHeader.CODE, code);
        json.put(GithubRequestHeader.REDIRECT_URI, authProviderProperties.getGithub().getRedirectUrl());
        return json;
    }

    private HttpHeaders createHeaders() {
        var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setAccessControlAllowOrigin(allowOrigin);
        return headers;
    }

    private void addTokenToHeaders(HttpHeaders headers, JsonNode accessToken) {
        headers.set(headerKeyForAuth, tokenPrefix + accessToken.asText());
    }

    private ResponseEntity<JsonNode> getAccessToken(ObjectNode body,
        HttpHeaders headers) {
        return restTemplate.exchange(
            authProviderProperties.getGithub().getLoginUrl(),
            HttpMethod.POST, new HttpEntity<>(body, headers), JsonNode.class);
    }

    private ResponseEntity<UserDTO> getUserByAccessToken(HttpHeaders headers) {
        return restTemplate.exchange(
            authProviderProperties.getGithub().getUserUrl(),
            HttpMethod.GET, new HttpEntity<>(null, headers), UserDTO.class);
    }

}