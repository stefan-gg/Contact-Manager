package com.ing.contactmanager.controllers.dtos.request.contactType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class RequestContactTypeDTO {

    private UUID uuid;

    @NotBlank()
    @Size(max = 50, message = "Contact type cannot be null")
    private String contactTypeName;
}
