package com.rkumarkravi.instaclone.config;

import com.rkumarkravi.instaclone.filter.CustomFilterForAuthentication;
import com.rkumarkravi.instaclone.filter.CustomFilterForAuthorization;
import com.rkumarkravi.instaclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    CustomFilterForAuthorization customFilterForAuthorization;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Bean
    public AuthenticationManager authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider::authenticate;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity/*,AuthenticationManager authenticationManager*/) throws Exception {

//        CustomFilterForAuthentication customFilterForAuthentication =
//                new CustomFilterForAuthentication(authenticationManager,secretKey);
//        customFilterForAuthentication.setFilterProcessesUrl("/v1/login");

        httpSecurity.authorizeHttpRequests(sc -> sc.requestMatchers(new String[]{"/public/**","/auth/**"}).permitAll() // Public endpoints
                .requestMatchers("/api/uploads/**").hasRole("USER") // Role-based access
                .requestMatchers("/api/**").authenticated());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.addFilter(customFilterForAuthentication);
        httpSecurity.addFilterBefore(customFilterForAuthorization, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}

