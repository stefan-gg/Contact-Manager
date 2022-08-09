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

    @NotBlank(message = "Contact type cannot be null")
    @Size(max = 50)
    private String contactTypeName;
}
