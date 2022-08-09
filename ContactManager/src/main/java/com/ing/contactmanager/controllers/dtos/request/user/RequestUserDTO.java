package com.ing.contactmanager.controllers.dtos.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class RequestUserDTO {
    private UUID uuid;

    @Email
    @Size(max = 100)
    private String email;

    @NotBlank()
    @Size(max = 255, message = "Password cannot be null")
    private String password;

    @NotBlank()
    @Size(max = 50, message = "First name cannot be null")
    private String firstName;

    @NotBlank()
    @Size(max = 50, message = "Last name cannot be null")
    private String lastName;

    @NotBlank()
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER")
    @Size(max = 20, message = "Role cannot be null")
    private String role;
}
