package com.ansv.taskmanagement.filter;

import com.ansv.taskmanagement.dto.redis.AccessToken;
import com.ansv.taskmanagement.handler.authentication.JwtTokenNotValidException;
import com.ansv.taskmanagement.service.RedisService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private RedisService redisService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        final String requestToken = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        if (DataUtils.notNullOrEmpty(requestToken)) {
            if (requestToken.startsWith("Bearer")) {
                jwtToken = requestToken.substring(7);
                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
                String uuid = jwtTokenProvider.getUUID(jwtToken);
                redisService.getToken("01GVYE6ESKRCKFWAFH1J94N9EQ");
//                AccessToken token = redisService.getTokenToRedis(uuid).get();
            } else {
                logger.warn("JWT token does not begin with Bearer string");
            }

            if (DataUtils.notNullOrEmpty(username)) {
                // call from service in message bus

            }

        }
        else {
            throw new JwtTokenNotValidException("JWT token not valid");
        }

        ContentCachingResponseWrapper responseCachingWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, response);
        // copy body to response
        responseCachingWrapper.copyBodyToResponse();

    }
}
