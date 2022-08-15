package com.ing.contactmanager.security.authentication;

import com.ing.contactmanager.entities.User;

public interface AuthenticationFacade {
    public User getLoggedInUser();

    public boolean canThisUserCreateNewUser();

    public String getEmailFromLoggedInUser();
    public boolean isLoggedUserAdmin(User user);
}
