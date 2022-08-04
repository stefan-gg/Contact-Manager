package com.ing.contactmanager.controllers;

import com.ing.contactmanager.entities.User;
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

    private final CRUDService<User> userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        return ResponseEntity.ok(userService.createOrUpdate(user));
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(userService.createOrUpdate(user));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid){
        userService.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(userService.getByUuid(uuid));
    }
}
