package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.controllers.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.controllers.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CRUDService<ResponseUserDTO, RequestUserDTO> userServiceDTO;
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userServiceDTO.getAll());
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<ResponseUserDTO>> getAllUsersFromPage(@RequestParam("page") int page, @RequestParam int size) {
        return ResponseEntity.ok(userServiceImpl.getUsersByPage(page, size));
    }

    @PostMapping
    public ResponseEntity<RequestUserDTO> save(@RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RequestUserDTO> update(@RequestBody RequestUserDTO requestUserDTO, @PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, uuid));
    }

    @GetMapping("/get-contacts/{uuid}")
    public ResponseEntity<List<ResponseContactDTO>> getContactsForUser(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceImpl.getContactsForUser(uuid));
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
