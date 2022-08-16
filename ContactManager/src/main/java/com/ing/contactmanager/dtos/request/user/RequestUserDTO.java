package com.ing.contactmanager.dtos.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestUserDTO {

    @Email
    @Size(max = 100)
    private String email;

    @NotBlank()
    @Size(max = 255)
    private String password;

    @NotBlank()
    @Size(max = 50)
    private String firstName;

    @NotBlank()
    @Size(max = 50)
    private String lastName;

    @NotBlank()
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER")
    @Size(max = 20)
    private String role;
}
