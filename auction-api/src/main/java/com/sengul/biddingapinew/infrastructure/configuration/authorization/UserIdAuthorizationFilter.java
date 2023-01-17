package com.sengul.biddingapinew.infrastructure.configuration.authorization;

import com.sengul.biddingapinew.infrastructure.utils.enums.HttpHeaders;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Objects;

public class UserIdAuthorizationFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!Objects.equals(request.getMethod(), HttpMethod.OPTIONS.name()) && !request.getServletPath().equals("/global/login")) {

            String userId = request.getHeader(HttpHeaders.X_USER_ID.key());

            if (userId == null || userId.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Request headers must contain X-USER-ID! X-USER-ID cannot be empty!");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            MDC.put(HttpHeaders.X_USER_ID.key(), userId);
        }

        filterChain.doFilter(request, servletResponse);
    }
}