package com.rkumarkravi.instaclone.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomFilterForAuthentication extends UsernamePasswordAuthenticationFilter {
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;
    private final AuthenticationManager authenticationManager;
    private final String secret;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("unsuccessfulAuthentication: {}", request.getParameter("username"));
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication: {}", request.getParameter("username"));
        User user = (User) authResult.getPrincipal();

        Map<String, Object> claims = new HashMap<>();

        String accessToken = Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

        String refreshToken = Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 20 * JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        Gson gson=new Gson();
        Map<String, String> res = new HashMap<>();
        res.put("access_token", accessToken);
        res.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(res));
    }
}
