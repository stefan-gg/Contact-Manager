package com.ing.contactmanager.controllers.dtos.post.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class PostContactDTO {
    private UUID uuid;
    @Email
    private String email;
    @NotBlank(message = "Contact's first name cannot be null")
    private String firstName;
    @NotBlank(message = "Contact's last name cannot be null")
    private String lastName;
    private String info;
    private String address;
    @NotBlank(message = "Phone number cannot be null")
    private String phoneNumber;
    //@NotBlank(message = "User cannot be null")
    //private PostUserDTO postUserDTO;
    //@NotBlank(message = "Contact type cannot be null")
    //private PostContactTypeDTO postContactTypeDTO;
    private String userEmail;
    private String contactTypeName;
}
