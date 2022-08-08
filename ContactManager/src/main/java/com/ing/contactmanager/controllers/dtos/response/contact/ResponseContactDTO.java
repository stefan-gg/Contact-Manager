package com.ing.contactmanager.controllers.dtos.response.contact;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseContactDTO {

//    private Integer id;
    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
    private String info;
    private String address;
    private String phoneNumber;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private User user;
//    private ContactType contactType;
}