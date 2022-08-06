package com.ing.contactmanager.controllers.dtos.post.contactType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostContactTypeDTO {

    @NotBlank
    private String contactTypeName;
}
