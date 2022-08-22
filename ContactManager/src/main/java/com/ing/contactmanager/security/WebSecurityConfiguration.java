package com.ing.contactmanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfiguration {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final SecurityUserDetailsService usersDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.cors()
                //.and()
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers("/users/**", "/contact-types/**").hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, "/contacts/*").hasAnyRole(USER, ADMIN)
                .antMatchers("/contacts/**").hasRole(USER)
                .antMatchers(HttpMethod.DELETE, "/contacts/*").hasAnyRole(USER, ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        authenticationProvider.setUserDetailsService(usersDetailsService);

        return authenticationProvider;
    }
}
