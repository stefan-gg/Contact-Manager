package com.ing.contactmanager.dtos.request.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestContactDTO {

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 50)
    @NotBlank()
    private String firstName;

    @Size(max = 50)
    @NotBlank()
    private String lastName;

    @Size(max = 100)
    private String info;

    @Size(max = 100)
    private String address;

    @NotBlank()
    @Size(max = 50)
    private String phoneNumber;

    @Size(max = 50)
    private String contactTypeName;
}
