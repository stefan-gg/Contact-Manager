package com.ing.contactmanager.controllers;

import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contact_types")
public class ContactTypeController {

    private final CRUDService<ContactType> contactTypeService;

    @GetMapping
    public ResponseEntity<List<ContactType>> getAll(){
        return ResponseEntity.ok(contactTypeService.getAll());
    }

    @PostMapping
    public ResponseEntity<ContactType> save(@RequestBody ContactType contactType){
        return ResponseEntity.ok(contactTypeService.createOrUpdate(contactType));
    }

    @PutMapping
    public ResponseEntity<ContactType> update(@RequestBody ContactType contactType){
        return ResponseEntity.ok(contactTypeService.createOrUpdate(contactType));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        contactTypeService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactType> getById(@PathVariable Integer id){
        return ResponseEntity.ok(contactTypeService.getById(id));
    }
}
