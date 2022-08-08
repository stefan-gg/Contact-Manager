package com.ing.contactmanager.controllers.dtos.response.user;

import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseUserDTO {

    private UUID uuid;
    private String email;
//    private String password;
    private String firstName;
    private String lastName;
    private String role;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
    private List<ResponseContactDTO> contacts;
}
