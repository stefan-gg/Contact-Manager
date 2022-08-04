package com.ing.contactmanager.controllers;

import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact_types")
public class ContactTypeController {

    private final CRUDService<ContactType> contactTypeService;

    @GetMapping
    public ResponseEntity<List<ContactType>> getAll(){
        return ResponseEntity.ok(contactTypeService.getAll());
    }

    @PostMapping
    public ResponseEntity<ContactType> save(@RequestBody ContactType contactType){
        contactType.setUid(UUID.randomUUID());
        return ResponseEntity.ok(contactTypeService.createOrUpdate(contactType));
    }

    @PutMapping
    public ResponseEntity<ContactType> update(@RequestBody ContactType contactType){
        return ResponseEntity.ok(contactTypeService.createOrUpdate(contactType));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid){
        contactTypeService.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContactType> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(contactTypeService.getByUuid(uuid));
    }
}
