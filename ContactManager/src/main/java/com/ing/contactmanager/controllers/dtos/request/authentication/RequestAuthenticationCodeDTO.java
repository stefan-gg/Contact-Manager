package com.ing.contactmanager.controllers.dtos.request.authentication;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class RequestAuthenticationCodeDTO {

    @Pattern(regexp = "/^\\+?[1-9][0-9]{7,14}$/")
    private String phoneNumber;

    @Size(min = 6)
    private String code;
}
