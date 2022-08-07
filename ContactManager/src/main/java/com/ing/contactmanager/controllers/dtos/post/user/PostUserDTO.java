package com.ing.contactmanager.controllers.dtos.post.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
public class PostUserDTO {
    private Integer id;
    private UUID uuid;
    @Email
    private String email;
    @NotBlank(message = "Password cannot be null")
    private String password;
    @NotBlank(message = "First name cannot be null")
    private String firstName;
    @NotBlank(message = "Last name cannot be null")
    private String lastName;
    @NotBlank(message = "Role cannot be null")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER")
    private String role;
}
