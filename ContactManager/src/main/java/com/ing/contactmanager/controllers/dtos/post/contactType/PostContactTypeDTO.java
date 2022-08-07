package com.ing.contactmanager.controllers.dtos.post.contactType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class PostContactTypeDTO {
    private Integer id;
    private UUID uuid;
    @NotBlank(message = "Contact type cannot be null")
    private String contactTypeName;
}
