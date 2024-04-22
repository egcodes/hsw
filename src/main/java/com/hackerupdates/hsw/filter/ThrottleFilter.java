package com.hackerupdates.hsw.filter;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.enums.ValidationRule;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@WebFilter("/*")
@Slf4j
public class ThrottleFilter implements Filter {

    private final LoadingCache<String, Integer> requestCountsPerIpAddress =
            Caffeine.newBuilder().expireAfterWrite(Constant.MAX_REQUESTS_WINDOW_IN_SECONDS, TimeUnit.SECONDS)
                    .build(key -> 0);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String clientIpAddress = getClientIP((HttpServletRequest) servletRequest);
        if(isMaximumRequestsPerSecondExceeded(clientIpAddress)){
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write(ValidationRule.TOO_MANY_REQUESTS.getError());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress) {
        Integer requests = 0;
        requests = requestCountsPerIpAddress.get(clientIpAddress);
        if(requests != null) {
            if(requests > Constant.MAX_REQUESTS_PER_WINDOW_INT) {
                requestCountsPerIpAddress.asMap().remove(clientIpAddress);
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                return true;
            }

        } else {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }

    @Override
    public void destroy() {
    }

}
