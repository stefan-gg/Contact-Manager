//package com.ing.contactmanager.security;
//
//import com.ing.contactmanager.services.impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class SecurityUserDetailsService implements UserDetailsService {
//
//    private final UserServiceImpl userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return new SecurityUserDetails(userService.getUserByEmail(email));
//    }
//}
