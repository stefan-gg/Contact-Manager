package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.get.contactType.ContactTypeDTO;
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

    private final CRUDService<ContactTypeDTO> contactTypeServiceDTO;

    @GetMapping
    public ResponseEntity<List<ContactTypeDTO>> getAll() {
        //return ResponseEntity.ok(contactTypeServiceDTO.getAll());
        List<ContactTypeDTO> contactTypeDTOList = contactTypeServiceDTO.getAll();
        return ResponseEntity.ok(contactTypeDTOList);
    }

    @PostMapping
    public ResponseEntity<ContactTypeDTO> save(@RequestBody ContactTypeDTO contactTypeDTO) {
//        contactType.setUid(UUID.randomUUID());
//        return ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(contactType));
        return null;
    }

    @PutMapping
    public ResponseEntity<ContactTypeDTO> update(@RequestBody ContactTypeDTO contactTypeDTO) {
        return null;//ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(contactType));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        //contactTypeServiceDTO.deleteByUuid(uuid);
        contactTypeServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContactTypeDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeServiceDTO.getByUuid(uuid));
    }
}
