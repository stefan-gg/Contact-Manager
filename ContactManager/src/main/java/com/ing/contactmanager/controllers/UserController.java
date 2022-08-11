package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CRUDService<ResponseUserDTO, RequestUserDTO> userServiceDTO;
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userServiceDTO.getAll());
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsersFromPage(Pageable pageable) {
        return ResponseEntity.ok(userServiceImpl.getUsersByPage(pageable));
    }

    @PostMapping
    public ResponseEntity<RequestUserDTO> save(@RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RequestUserDTO> update(@RequestBody RequestUserDTO requestUserDTO, @PathVariable UUID uuid) {

        return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, uuid));
    }

    @GetMapping("/{uuid}/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getContactsForUser(@PathVariable UUID uuid, Pageable pageable) {
        return ResponseEntity.ok(userServiceImpl.getContactsForUser(uuid, pageable));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        userServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceDTO.getByUuid(uuid));
    }
}
