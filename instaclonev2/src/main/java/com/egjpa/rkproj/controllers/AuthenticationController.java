package com.egjpa.rkproj.controllers;

import com.egjpa.rkproj.config.security.JwtTokenUtil;
import com.egjpa.rkproj.config.security.TokenRefreshException;
import com.egjpa.rkproj.dto.req.AuthenticationReqDto;
import com.egjpa.rkproj.dto.req.TokenRefreshReqDto;
import com.egjpa.rkproj.dto.res.AuthenticationResDto;
import com.egjpa.rkproj.dto.res.TokenRefreshResDto;
import com.egjpa.rkproj.models.RefreshToken;
import com.egjpa.rkproj.models.User;
import com.egjpa.rkproj.repos.UserRepository;
import com.egjpa.rkproj.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationReqDto authenticationRequest) {
        log.info("createAuthenticationToken :{}", authenticationRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final Optional<User> userDetails = userDetailsService.findByUsernameOrEmail(authenticationRequest.getUsername());
       log.info("authentication: "+authentication.getCredentials());
        if (authentication.isAuthenticated() && userDetails.isPresent()) {
            final String authToken = jwtTokenUtil.generateToken(userDetails.get());
            final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.get());

            return ResponseEntity.ok(new AuthenticationResDto(authToken, refreshToken.getToken()));

        } else {
            return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
        }
    }

    //to refresh the auth token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshReqDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenUtil.generateToken(userDetailsService.findByUsernameOrEmail(user.getUsername()).get());
                    return ResponseEntity.ok(new TokenRefreshResDto(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
