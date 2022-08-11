package com.ing.contactmanager.dtos.response.contactType;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseContactTypeDTO {

    private UUID uuid;

    private String contactTypeName;
}
