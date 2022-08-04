package com.ing.contactmanager.dtos;

import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ContactDTO {
    private Integer id;
    private UUID uuid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String info;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private ContactType contactType;
}