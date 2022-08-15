package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
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
@RequestMapping
public class ContactController {

    private final ContactServiceImpl contactService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getAllContacts(Pageable pageable) {

        User loggedInUser = authenticationFacade.getLoggedInUser();

        if (!authenticationFacade.isLoggedUserAdmin(loggedInUser)) {
            return ResponseEntity.ok(contactService.getContacts(pageable, loggedInUser));
        }

        throw new AccessDeniedException("Access denied");
    }

    @GetMapping("/users/{uuid}/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getContactsForUser(@PathVariable UUID uuid,
                                                                       Pageable pageable)
            throws java.nio.file.AccessDeniedException {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        if (authenticationFacade.isLoggedUserAdmin(loggedInUser)) {
            return ResponseEntity.ok(contactService.getContactsForUser(uuid, pageable));
        }

        throw new java.nio.file.AccessDeniedException("Access denied");
    }

    @GetMapping("/contacts/{uuid}")
    public ResponseEntity<ResponseContactDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactService.getByUuid(uuid));
    }

    @PostMapping("/contacts")
    public ResponseEntity<ResponseContactDTO> save(
            @RequestBody RequestContactDTO postRequestContactDTO)
            throws java.nio.file.AccessDeniedException {
        return ResponseEntity.ok(contactService.createOrUpdate(postRequestContactDTO, null,
                authenticationFacade.getEmailFromLoggedInUser()));
    }

    @PutMapping("/contacts/{uuid}")
    public ResponseEntity<ResponseContactDTO> update(
            @RequestBody RequestContactDTO postRequestContactDTO, @PathVariable UUID uuid)
            throws java.nio.file.AccessDeniedException {
        return ResponseEntity.ok(contactService.createOrUpdate(postRequestContactDTO, uuid,
                authenticationFacade.getEmailFromLoggedInUser()));
    }

    @DeleteMapping("/contacts/{uuid}")
    public void delete(@PathVariable UUID uuid) throws java.nio.file.AccessDeniedException {
        contactService.deleteByUuid(uuid, authenticationFacade.getEmailFromLoggedInUser());
    }
}
