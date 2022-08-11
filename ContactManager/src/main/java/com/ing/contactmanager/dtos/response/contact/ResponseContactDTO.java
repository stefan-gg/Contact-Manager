package com.ing.contactmanager.dtos.response.contact;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseContactDTO {

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
