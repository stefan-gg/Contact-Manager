package com.ing.contactmanager.controllers.dtos.get.user;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
//    private Integer id;
//    private UUID uuid;
    private String email;
//    private String password;
    private String firstName;
    private String lastName;
    private String role;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
    private List<ContactDTO> contacts;
}
