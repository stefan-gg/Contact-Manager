package com.ing.contactmanager.controllers.dtos.request.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class RequestContactDTO {
    private UUID uuid;
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 50)
    @NotBlank(message = "Contact's first name cannot be null")
    private String firstName;

    @Size(max = 50)
    @NotBlank(message = "Contact's last name cannot be null")
    private String lastName;

    @Size(max = 100)
    private String info;

    @Size(max = 100)
    private String address;

    @NotBlank(message = "Phone number cannot be null")
    @Size(max = 50)
    private String phoneNumber;

    //@NotBlank(message = "User cannot be null")
    //private RequestUserDTO postUserDTO;
    //@NotBlank(message = "Contact type cannot be null")
    //private RequestContactTypeDTO postContactTypeDTO;

    @Size(max = 100)
    private String userEmail;

    @Size(max = 50)
    private String contactTypeName;
}
