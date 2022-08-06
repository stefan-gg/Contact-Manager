package com.ing.contactmanager.controllers.dtos.post.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUserDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
