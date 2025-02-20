package com.example.newsfeed.common.interceptor;

import com.example.newsfeed.auth.exception.UnAuthorizedException;
import com.example.newsfeed.common.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Set;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Set<String> WHITE_LIST_GET_ENDPOINTS = Set.of(
            "/api/v1/posts",
            "/api/v1/posts/{id}",
            "/api/v1/comments",
            "/api/v1/comments/{id}"
    );


    private final PathPatternParser patternParser = new PathPatternParser();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String httpMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        if ("GET".equalsIgnoreCase(httpMethod) && isWhitelisted(requestURI)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            throw new UnAuthorizedException();
        }

        return true;
    }

    private boolean isWhitelisted(String requestURI) {
        return WHITE_LIST_GET_ENDPOINTS.stream().anyMatch(pattern -> {
            PathPattern pathPattern = patternParser.parse(pattern);
            return pathPattern.matches(PathContainer.parsePath(requestURI));
        });
    }
}
