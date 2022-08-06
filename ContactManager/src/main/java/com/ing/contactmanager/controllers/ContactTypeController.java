package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.get.contactType.ContactTypeDTO;
import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
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

    private final CRUDService<ContactTypeDTO, PostContactTypeDTO> contactTypeServiceDTO;

    @GetMapping
    public ResponseEntity<List<ContactTypeDTO>> getAll() {
        return ResponseEntity.ok(contactTypeServiceDTO.getAll());
    }

    @PostMapping
    public ResponseEntity<PostContactTypeDTO> save(@RequestBody PostContactTypeDTO postContactTypeDTO) {
        return ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(postContactTypeDTO, null));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PostContactTypeDTO> update(@RequestBody PostContactTypeDTO postContactTypeDTO, @PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(postContactTypeDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        contactTypeServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContactTypeDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeServiceDTO.getByUuid(uuid));
    }
}
