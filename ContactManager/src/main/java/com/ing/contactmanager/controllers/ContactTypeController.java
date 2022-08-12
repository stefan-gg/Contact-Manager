package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact-types")
public class ContactTypeController {

    private final CRUDService<ResponseContactTypeDTO, RequestContactTypeDTO> contactTypeServiceDTO;

    @GetMapping
    public ResponseEntity<Page<ResponseContactTypeDTO>> getAll() {
        return ResponseEntity.ok(contactTypeServiceDTO.getAll());
    }

    @PostMapping
    public ResponseEntity<ResponseContactTypeDTO> save(@RequestBody RequestContactTypeDTO requestContactTypeDTO) {
        return ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(requestContactTypeDTO, null));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseContactTypeDTO> update(@RequestBody RequestContactTypeDTO requestContactTypeDTO, @PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeServiceDTO.createOrUpdate(requestContactTypeDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        contactTypeServiceDTO.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseContactTypeDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeServiceDTO.getByUuid(uuid));
    }
}
