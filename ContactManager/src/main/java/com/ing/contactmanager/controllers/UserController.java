package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CRUDService<ResponseUserDTO, RequestUserDTO> userServiceDTO;
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsersFromPage(Pageable pageable) {
        return ResponseEntity.ok(userServiceImpl.getUsers(pageable));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> save(@RequestBody RequestUserDTO requestUserDTO)
            throws AccessDeniedException {

        if (authenticationFacade.canThisUserCreateNewUser()) {
            return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, null));
        }
        throw new AccessDeniedException("Access denied");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> update(@RequestBody RequestUserDTO requestUserDTO,
                                                  @PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceDTO.createOrUpdate(requestUserDTO, uuid));
    }

    @GetMapping("/{uuid}/contacts")
    public ResponseEntity<Page<ResponseContactDTO>> getContactsForUser(@PathVariable UUID uuid,
                                                                       Pageable pageable)
            throws AccessDeniedException {

        if (authenticationFacade.isLoggedUserAdmin()) {
            return ResponseEntity.ok(userServiceImpl.getContactsForUser(uuid, pageable));
        }
        throw new AccessDeniedException("Access denied");
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) throws AccessDeniedException {
        userServiceDTO.deleteByUuid(uuid);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> getById(@PathVariable UUID uuid)
            throws AccessDeniedException {
        if (authenticationFacade.isLoggedUserAdmin()) {
            return ResponseEntity.ok(userServiceDTO.getByUuid(uuid));
        }
        throw new AccessDeniedException("Access denied");
    }
}
