package com.ing.contactmanager.security.authentication;

import com.ing.contactmanager.entities.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    public User getLoggedInUser();
}
