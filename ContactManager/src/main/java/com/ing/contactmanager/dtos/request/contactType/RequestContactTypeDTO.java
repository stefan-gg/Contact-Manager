package com.ing.contactmanager.dtos.request.contactType;

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
    @Size(max = 50, message = "Contact type cannot be null or greater than 50")
    private String contactTypeName;
}
