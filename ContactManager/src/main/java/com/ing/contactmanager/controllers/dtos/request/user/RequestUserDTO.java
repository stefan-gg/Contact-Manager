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

    @NotBlank(message = "Password cannot be null")
    @Size(max = 255)
    private String password;

    @NotBlank(message = "First name cannot be null")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name cannot be null")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Role cannot be null")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER")
    @Size(max = 20)
    private String role;
}
