//package com.ing.contactmanager.security;
//
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    private final SecurityUserDetailsService usersDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //super.configure(auth);
//        auth.authenticationProvider(authProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/admin/*").hasRole("ADMIN")
//                .antMatchers("/course/*").hasRole("AUTHOR")
//                .antMatchers("/order/*").hasRole("USER")
//                .antMatchers("/*").hasAnyRole("USER", "AUTHOR", "ADMIN")
//                .antMatchers("/view/*").hasAnyRole("USER", "AUTHOR")
//                .antMatchers("/register").anonymous()
//                .and()
//                .formLogin();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
//        authenticationProvider.setUserDetailsService(usersDetailsService);
//
//        return authenticationProvider;
//    }
//}
