package com.rk.animestream.config;

import com.rk.animestream.filter.CustomFilterForAuthentication;
import com.rk.animestream.filter.CustomFilterForAuthorization;
import com.rk.animestream.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAuthService userService;

    @Autowired
    CustomFilterForAuthorization customFilterForAuthorization;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests().anyRequest().permitAll();
//        http.headers().frameOptions().disable();
        CustomFilterForAuthentication customFilterForAuthentication = new CustomFilterForAuthentication(authenticationManagerBean(),secretKey);
        customFilterForAuthentication.setFilterProcessesUrl("/login");
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/auth/**","/login","/api/v1/download/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customFilterForAuthentication);
        http.addFilterBefore(customFilterForAuthorization, UsernamePasswordAuthenticationFilter.class);
    }

}
