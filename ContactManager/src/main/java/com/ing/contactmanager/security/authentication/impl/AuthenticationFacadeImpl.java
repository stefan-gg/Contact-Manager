package com.ing.contactmanager.security.authentication.impl;

import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserRepository userRepository;

    @Override
    public User getLoggedInUser() {
        String userEmail = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userEmail = authentication.getName();
        } else {
            try {
                throw new AccessDeniedException("Access denied");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        final String email = userEmail;
        return userRepository.getUserByEmail(userEmail)
                .orElseThrow(() -> new NoSuchElementException("User with email : " + email + " does not exist"));
    }
}
