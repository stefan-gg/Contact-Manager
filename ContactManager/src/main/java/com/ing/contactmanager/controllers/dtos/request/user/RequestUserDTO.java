package com.ing.contactmanager.controllers.dtos.request.user;

import com.ing.contactmanager.entities.enums.VerificationStatus;
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
    @Pattern(regexp = "^(?=.{1,100}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\" +
            ".[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
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

    @NotBlank
    @Pattern(regexp = "VERIFIED|UNVERIFIED")
    @Size(max = 20)
    private VerificationStatus verificationStatus;

    @Pattern(regexp = "/^\\+?[1-9][0-9]{7,14}$/")
    @Size(max = 50, message = "Phone number length cannot be greater than 50")
    private String phoneNumber;
}
