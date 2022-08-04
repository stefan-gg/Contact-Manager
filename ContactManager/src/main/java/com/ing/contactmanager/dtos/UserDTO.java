package com.ing.contactmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private Integer id;
    //private String uuid;
    private UUID uuid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
