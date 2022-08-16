package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import com.ing.contactmanager.services.impl.ContactServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ContactController {

    private final ContactServiceImpl contactService;
    private final AuthenticationFacade authenticationFacade;


    @GetMapping("/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getAllContactsForUser(
            @PageableDefault(size = 5) Pageable pageable) {

        User loggedInUser = authenticationFacade.getLoggedInUser();

        return ResponseEntity.ok(contactService.getContacts(pageable, loggedInUser));
    }

//    @GetMapping(value = "/contacts")
//    public ResponseEntity<Page<ResponseContactDTO>> getContactsByTheSearchParam(
//            @And({
//                    @Spec(path = "firstName", spec = Like.class),
//                    @Spec(path = "lastName", spec = Like.class),
//                    @Spec(path = "phoneNumber", spec = Like.class),
//                    @Spec(path = "email", spec = Like.class),
//            }) Specification<Contact> contactSpecification,
//            Pageable pageable) {
//        return ResponseEntity.ok(contactService.getContactsBySearchQuery(searchParam,
//                authenticationFacade.getEmailFromLoggedInUser(), pageable));
//    }

    @GetMapping("/users/{uuid}/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getContactsForUserAsAdmin(
            @PathVariable UUID uuid,
            @PageableDefault(size = 5) Pageable pageable)
            throws AccessDeniedException {
        return ResponseEntity.ok(contactService.getContactsForUser(uuid, pageable));
    }

    @GetMapping("/contacts/{uuid}")
    public ResponseEntity<ResponseContactDTO> getById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contactService.getByUuid(uuid));
    }

    @PostMapping("/contacts")
    public ResponseEntity<ResponseContactDTO> save(
           @Valid @RequestBody RequestContactDTO postRequestContactDTO)
            throws AccessDeniedException {
        return ResponseEntity.ok(contactService.createOrUpdate(postRequestContactDTO, null,
                authenticationFacade.getEmailFromLoggedInUser()));
    }

    @PutMapping("/contacts/{uuid}")
    public ResponseEntity<ResponseContactDTO> update(
          @Valid @RequestBody RequestContactDTO postRequestContactDTO, @PathVariable UUID uuid)
            throws AccessDeniedException {
        return ResponseEntity.ok(contactService.createOrUpdate(postRequestContactDTO, uuid,
                authenticationFacade.getEmailFromLoggedInUser()));
    }

    @DeleteMapping("/contacts/{uuid}")
    public void delete(@PathVariable UUID uuid) throws AccessDeniedException {
        contactService.deleteByUuid(uuid, authenticationFacade.getEmailFromLoggedInUser());
    }
}
