package com.ing.contactmanager.controllers.dtos.post.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostContactDTO {

    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String info;
    private String address;
    @NotBlank
    private String phoneNumber;
}
