package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CRUDService<UserDTO> userServiceDTO;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        //return ResponseEntity.ok(userServiceDTO.getAll());
        List<UserDTO> userDTOList = userServiceDTO.getAll();
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
        return null;//ResponseEntity.ok(userServiceDTO.createOrUpdate(user));
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO){
        return null;//ResponseEntity.ok(userServiceDTO.createOrUpdate(user));
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
