package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.controllers.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.services.impl.UserServiceImpl;
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
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping()
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsersFromPage(
            @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(userServiceImpl.getUsers(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> getById(@PathVariable UUID uuid) {

        return ResponseEntity.ok(userServiceImpl.getByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> save(@Valid @RequestBody RequestUserDTO requestUserDTO) {

        return ResponseEntity.ok(userServiceImpl.createOrUpdate(requestUserDTO, null));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseUserDTO> update(@Valid @RequestBody RequestUserDTO requestUserDTO,
                                                  @PathVariable UUID uuid) {
        return ResponseEntity.ok(userServiceImpl.createOrUpdate(requestUserDTO, uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) throws AccessDeniedException {
        userServiceImpl.deleteByUuid(uuid);
    }
}