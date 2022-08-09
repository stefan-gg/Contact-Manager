package com.ing.contactmanager.controllers.dtos.response.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ResponseUserDTO {

    private UUID uuid;

    private String email;

    private String firstName;

    private String lastName;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
