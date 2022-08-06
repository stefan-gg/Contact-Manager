package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.controllers.dtos.post.contact.PostContactDTO;
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

    private final CRUDService<ContactDTO, PostContactDTO> contactServiceDTO;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAll(){
        return ResponseEntity.ok(contactServiceDTO.getAll());
    }

    @PostMapping
    public ResponseEntity<PostContactDTO> save(@RequestBody PostContactDTO postContactDTO){
        return ResponseEntity.ok(contactServiceDTO.createOrUpdate(postContactDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PostContactDTO> update(@RequestBody PostContactDTO postContactDTO, @PathVariable UUID uuid){
        return ResponseEntity.ok(contactServiceDTO.createOrUpdate(postContactDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        contactServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContactDTO> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(contactServiceDTO.getByUuid(uuid));
    }
}
