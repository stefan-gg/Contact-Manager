package com.ing.contactmanager.controllers.dtos.response.contactType;

import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseContactTypeDTO {

//    private Integer id;
    private UUID uuid;
    private String contactTypeName;
    private List<ResponseContactDTO> contacts;
}
