package com.ing.contactmanager.controllers.dtos.post.contact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostContactDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String info;
    private String address;
    private String phoneNumber;
}
