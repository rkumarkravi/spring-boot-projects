package com.rkumarkravi.instaclone.filter;

import com.rkumarkravi.instaclone.dao.sql.IJwtTokenRepository;
import com.rkumarkravi.instaclone.dto.ApiResponse;
import com.rkumarkravi.instaclone.entity.IJwtToken;
import com.rkumarkravi.instaclone.service.UserService;
import com.rkumarkravi.instaclone.util.JWTUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.rkumarkravi.instaclone.util.Utils.gson;

@Component
@Slf4j
public class CustomFilterForAuthorization extends OncePerRequestFilter {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Autowired
    private IJwtTokenRepository iJwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String mode = httpServletRequest.getHeader("mode");
        String token = null;
        String userName = null;
        try {

            if (null == mode) {
                mode = "ANONYMOUS";
            }

            if (null != authorization && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                userName = jwtUtility.getUsernameFromToken(token);
            }

            if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails
                        = userService.loadUserByUsername(userName);

                Optional<IJwtToken> tokenPresent = iJwtTokenRepository.findByDeviceModeAndAuthTokenAndUser_Username(mode, token, userName);
                if (!tokenPresent.isPresent()) {
                    throw new Exception("Invalid Token");
                }
                if (jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }
        } catch (Exception ex) {
            log.error("exception is :: {}", ex.getLocalizedMessage());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiResponse<String> exceptionResponse = new ApiResponse<>();
            exceptionResponse.setStatus("F");
            exceptionResponse.setMessage("Exception: " + ex.getMessage());
            exceptionResponse.setPath(httpServletRequest.getRequestURI());
            httpServletResponse.getWriter().write(gson.toJson(exceptionResponse));
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}