package com.hackerswork.hsw.service.filter;

import static com.hackerswork.hsw.constants.Constant.AUTHENTICATION_PATH;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.hackerswork.hsw.constants.Constant.GithubRequestHeader;
import com.hackerswork.hsw.persistence.entity.Person;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
@Slf4j
public class AuthenticationFilter implements Filter {

    @Getter
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var url = ((RequestFacade) req).getRequestURI();
        var userName = ((RequestFacade) req).getHeader(Person.Meta.userName);
        var code = ((RequestFacade) req).getHeader(GithubRequestHeader.CODE);

        if (!url.contains(AUTHENTICATION_PATH) && nonNull(userName)) {
            var cachedCode = cache.get(userName);
            if (isNull(cachedCode) || !cachedCode.equals(code)) {
                log.warn("Invalid code: {}", code);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}