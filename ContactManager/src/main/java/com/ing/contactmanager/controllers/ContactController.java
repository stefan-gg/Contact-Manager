package com.ing.contactmanager.controllers;

import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final CRUDService<Contact> contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAll(){
        return ResponseEntity.ok(contactService.getAll());
    }

    @PostMapping
    public ResponseEntity<Contact> save(@RequestBody Contact contact){
        contact.setUid(UUID.randomUUID());
        return ResponseEntity.ok(contactService.createOrUpdate(contact));
    }

    @PutMapping
    public ResponseEntity<Contact> update(@RequestBody Contact contact){
        return ResponseEntity.ok(contactService.createOrUpdate(contact));
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        contactService.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Contact> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(contactService.getByUuid(uuid));
    }
}
