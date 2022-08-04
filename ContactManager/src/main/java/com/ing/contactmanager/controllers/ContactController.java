package com.ing.contactmanager.controllers;

import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(contactService.createOrUpdate(contact));
    }

    @PutMapping
    public ResponseEntity<Contact> update(@RequestBody Contact contact){
        return ResponseEntity.ok(contactService.createOrUpdate(contact));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        contactService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable Integer id){
        return ResponseEntity.ok(contactService.getById(id));
    }
}
