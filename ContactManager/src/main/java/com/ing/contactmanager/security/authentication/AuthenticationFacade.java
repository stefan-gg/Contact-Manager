package com.ing.contactmanager.security.authentication;

import com.ing.contactmanager.entities.User;

public interface AuthenticationFacade {
    User getLoggedInUser();

    String getEmailFromLoggedInUser();

    boolean isAdmin();
}
