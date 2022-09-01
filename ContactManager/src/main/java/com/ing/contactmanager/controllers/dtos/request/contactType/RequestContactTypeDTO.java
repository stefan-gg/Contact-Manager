package com.ing.contactmanager.controllers.dtos.request.contactType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestContactTypeDTO {

    @NotBlank()
    @Size(max = 50)
    private String contactTypeName;
}
