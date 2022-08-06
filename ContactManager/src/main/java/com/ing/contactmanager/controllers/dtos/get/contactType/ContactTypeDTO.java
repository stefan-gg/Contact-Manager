package com.ing.contactmanager.controllers.dtos.get.contactType;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactTypeDTO {

//    private Integer id;
//    private UUID uuid;
    private String contactTypeName;
    private List<ContactDTO> contacts;
}
