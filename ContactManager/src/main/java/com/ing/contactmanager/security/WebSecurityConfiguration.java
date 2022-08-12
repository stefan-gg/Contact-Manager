package com.ing.contactmanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final SecurityUserDetailsService usersDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/").permitAll()

                .antMatchers("/contact-types/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, /*"/users/**",*/ "/contacts/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/*", "/contact-types/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/users/{uuid}/contacts").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/contacts/").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/contacts/*").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/contacts/*").hasAnyRole("USER", "ADMIN")
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
