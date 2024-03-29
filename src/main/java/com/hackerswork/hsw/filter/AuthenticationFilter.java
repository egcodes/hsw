package com.hackerswork.hsw.filter;

import static com.hackerswork.hsw.constants.Constant.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.persistence.entity.Token;
import com.hackerswork.hsw.service.security.TokenService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@WebFilter("/*")
@Slf4j
public class AuthenticationFilter implements Filter {

    private final TokenService tokenService;

    private final List<String> blackList = List.of(Path.LOGIN, Path.SIGN_UP, Path.SIGN_IN, Path.COOKIE, Path.SWAGGER_UI, Path.API_DOCS);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var url = ((RequestFacade) req).getRequestURI();
        var token = getCookieValue((HttpServletRequest) req);
        Token cachedToken = null;

        if (url.contains(Path.TOKEN_VALIDATE)) {
            cachedToken = tokenService.getFromDB(token);
            if (isInvalidToken(cachedToken, token, (ResponseFacade) resp)) {
                return;
            }
        }

        if (blackList.stream().noneMatch(url::contains)) {
            cachedToken = tokenService.get(token);
            if (isNull(cachedToken) || !cachedToken.getToken().equals(token)) {
                cachedToken = tokenService.getFromDB(token);
                if (isInvalidToken(cachedToken, token, (ResponseFacade) resp))
                    return;
            }
        }

        var request = (HttpServletRequest) req;
        var mutableRequest = new MutableHttpServletRequest(request);
        if (nonNull(cachedToken)) {
            mutableRequest.addHeader(Constant.PERSON_ID, cachedToken.getPersonId().toString());
            mutableRequest.addHeader(Constant.TOKEN, cachedToken.getToken());
        }

        chain.doFilter(mutableRequest, resp);
    }

    private boolean isInvalidToken(Token cachedToken, String token, ResponseFacade resp) throws IOException {
        if (isNull(cachedToken) || !cachedToken.getToken().equals(token)) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), ValidationRule.INVALID_TOKEN.getError());
            return true;
        }
        return false;
    }

    private String getCookieValue(HttpServletRequest req) {
        if (nonNull(req.getCookies()))
            return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(Constant.COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        return "-";
    }

    @Override
    public void destroy() {
    }
}