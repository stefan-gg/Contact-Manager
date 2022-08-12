package com.ing.contactmanager.security.authentication;

import com.ing.contactmanager.entities.User;

public interface AuthenticationFacade {
    public User getLoggedInUser();

    public boolean canThisUserCreateNewUser();

    public boolean isLoggedUserAdmin();
}
