package com.ing.contactmanager.controllers.dtos.post.contactType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class PostContactTypeDTO {

    private UUID uuid;
    @NotBlank
    private String contactTypeName;
}
