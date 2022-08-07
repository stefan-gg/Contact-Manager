package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.controllers.dtos.post.user.PostUserDTO;
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

    private final CRUDService<UserDTO, PostUserDTO> userServiceDTO;
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userServiceDTO.getAll());
    }

    @PostMapping
    public ResponseEntity<PostUserDTO> save(@RequestBody PostUserDTO postUserDTO){
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(postUserDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PostUserDTO> update(@RequestBody PostUserDTO postUserDTO, @PathVariable UUID uuid){
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(postUserDTO, uuid));
    }

    @GetMapping("/get-contacts/{uuid}")
    public ResponseEntity<List<ContactDTO>> getContactsForUser(@PathVariable UUID uuid){
      return ResponseEntity.ok(userServiceImpl.getContactsForUser(uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid){
        userServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(userServiceDTO.getByUuid(uuid));
    }
}
