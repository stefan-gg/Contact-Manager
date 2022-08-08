package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.controllers.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.impl.ContactServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final CRUDService<ResponseContactDTO, RequestContactDTO> contactServiceDTO;
    private final ContactServiceImpl contactService;

    @GetMapping
    public ResponseEntity<List<ResponseContactDTO>> getAll(){
        return ResponseEntity.ok(contactServiceDTO.getAll());
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<ResponseContactDTO>> getAllContactsFromPage(@RequestParam("page") int page, @RequestParam int size){
        return ResponseEntity.ok(contactService.getContactsByPage(page, size));
    }

    @PostMapping
    public ResponseEntity<RequestContactDTO> save(@RequestBody RequestContactDTO postRequestContactDTO){
        return ResponseEntity.ok(contactServiceDTO.createOrUpdate(postRequestContactDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RequestContactDTO> update(@RequestBody RequestContactDTO postRequestContactDTO, @PathVariable UUID uuid){
        return ResponseEntity.ok(contactServiceDTO.createOrUpdate(postRequestContactDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        contactServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseContactDTO> getById(@PathVariable UUID uuid){
        return ResponseEntity.ok(contactServiceDTO.getByUuid(uuid));
    }
}
