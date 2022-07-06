package com.hackerswork.hsw.filter;

import static com.hackerswork.hsw.constants.Constant.AUTHENTICATION_PATH;
import static java.util.Objects.isNull;

import com.hackerswork.hsw.constants.Constant.GithubRequestHeader;
import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.persistence.entity.Person;
import com.hackerswork.hsw.service.security.TokenService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
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

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var url = ((RequestFacade) req).getRequestURI();
        var userName = ((RequestFacade) req).getHeader(Person.Meta.userName);
        var code = ((RequestFacade) req).getHeader(GithubRequestHeader.CODE);

        if (!url.contains(AUTHENTICATION_PATH)) {
            var cachedCode = tokenService.get(userName);
            if (isNull(cachedCode) || !cachedCode.equals(code)) {
                log.warn("Invalid token: {}, for userName: {}", code, userName);
                ((ResponseFacade) resp).sendError(HttpStatus.UNAUTHORIZED.value(), ValidationRule.INVALID_TOKEN.getError());
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}