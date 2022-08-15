package com.ing.contactmanager.security.authentication.impl;

import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import com.ing.contactmanager.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserServiceImpl userService;

    @Override
    public User getLoggedInUser() {
        String userEmail;

        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userEmail = authentication.getName();
        } else {
            try {
                throw new AccessDeniedException("Access denied");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return userService.getUserByEmail(userEmail);
    }

    @Override
    public boolean canThisUserCreateNewUser() {

        Authentication authentication = getAuthentication();

        return userService.getUserByEmail(authentication.getName()).getRole().toString().equals(
                ROLE_ADMIN);
    }

    @Override
    public String getEmailFromLoggedInUser() {
        return getAuthentication().getName();
    }

    @Override
    public boolean isLoggedUserAdmin(User user) {
        return user.getRole().toString().equals(ROLE_ADMIN);
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
