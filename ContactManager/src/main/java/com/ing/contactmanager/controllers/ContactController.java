package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.impl.ContactServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final CRUDService<ResponseContactDTO, RequestContactDTO> contactServiceDTO;
    private final ContactServiceImpl contactService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<ResponseContactDTO>> getAllContacts(Pageable pageable) {
        if (!authenticationFacade.isLoggedUserAdmin()) {
            User loggedInUser = authenticationFacade.getLoggedInUser();

            return ResponseEntity.ok(contactService.getContacts(pageable, loggedInUser));
        }

        throw new AccessDeniedException("Access denied");
    }

    @PostMapping
    public ResponseEntity<ResponseContactDTO> save(@RequestBody RequestContactDTO postRequestContactDTO){
        return ResponseEntity.ok(contactServiceDTO.createOrUpdate(postRequestContactDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseContactDTO> update(@RequestBody RequestContactDTO postRequestContactDTO, @PathVariable UUID uuid){
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
