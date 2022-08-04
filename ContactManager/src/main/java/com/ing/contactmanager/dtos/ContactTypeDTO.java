package com.ing.contactmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContactTypeDTO {
    private Integer id;
    private UUID uuid;
    private String contactTypeName;
}
