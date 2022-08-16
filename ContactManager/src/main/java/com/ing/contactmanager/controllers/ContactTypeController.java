package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.services.impl.ContactTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact-types")
public class ContactTypeController {

    private final ContactTypeServiceImpl contactTypeService;

    @GetMapping()
    public ResponseEntity<Page<ResponseContactTypeDTO>> getAllContactTypes(
            @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(contactTypeService.getAll(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseContactTypeDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeService.getByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<ResponseContactTypeDTO> save(@Valid @RequestBody RequestContactTypeDTO requestContactTypeDTO) {
        return ResponseEntity.ok(contactTypeService.createOrUpdate(requestContactTypeDTO, null));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseContactTypeDTO> update(@Valid @RequestBody RequestContactTypeDTO requestContactTypeDTO, @PathVariable UUID uuid) {
        return ResponseEntity.ok(contactTypeService.createOrUpdate(requestContactTypeDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        contactTypeService.deleteByUuid(uuid);
    }
}
