package com.ing.contactmanager.controllers;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
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

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsersFromPage(Pageable pageable) {
        return ResponseEntity.ok(userServiceImpl.getUsers(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> getById(@PathVariable UUID uuid)
            throws AccessDeniedException {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        if (authenticationFacade.isLoggedUserAdmin(loggedInUser)) {
            return ResponseEntity.ok(userServiceImpl.getByUuid(uuid));
        }

        throw new AccessDeniedException("Access denied");
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> save(@RequestBody RequestUserDTO requestUserDTO)
            throws AccessDeniedException {

        if (authenticationFacade.canThisUserCreateNewUser()) {
            return ResponseEntity.ok(userServiceImpl.createOrUpdate(requestUserDTO, null));
        }
        throw new AccessDeniedException("Access denied");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> update(@RequestBody RequestUserDTO requestUserDTO,
                                                  @PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceImpl.createOrUpdate(requestUserDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) throws AccessDeniedException {
        userServiceImpl.deleteByUuid(uuid);
    }
}
