package com.hackerswork.hsw.service.filter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.hackerswork.hsw.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
@Slf4j
public class StatsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var start = Instant.now();
        try {
            chain.doFilter(req, resp);
        } finally {
            var finish = Instant.now();
            var time = Duration.between(start, finish).toMillis();
            var path = ((HttpServletRequest) req).getServletPath();

            if (time > Constant.MAX_LIMIT_TIME)
                log.info("{}: {} ms ", ((HttpServletRequest) req).getRequestURI(), time);
        }
    }

    @Override
    public void destroy() {
    }
}