package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.controllers.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CRUDService<ResponseUserDTO, RequestUserDTO> {

    private final UserRepository userRepository;
    private final ContactServiceImpl contactService;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDTO getByUuid(UUID uuid) {
        return userMapper.convertToUserDTO(userRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestUserDTO createOrUpdate(RequestUserDTO requestUserDTO, UUID uuid) {
        if (uuid == null) {
            requestUserDTO.setUuid(UUID.randomUUID());
            userRepository.save(userMapper.convertPostUserDTOToUser(requestUserDTO));

        } else {
            User user = getUserByUuid(uuid);
            User updatedUser = userMapper.convertPostUserDTOToUser(requestUserDTO);

            updatedUser.setId(user.getId());

            userRepository.save(updatedUser);

        }
        requestUserDTO.setPassword(null);

        return requestUserDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseUserDTO> getAll() {
        return new PageImpl<>(userMapper.getAllUsers(userRepository.findAllByOrderByLastNameAsc()));
    }

    public Page<ResponseContactDTO> getContactsForUser(UUID uuid, Pageable pageable) {
        return new PageImpl<>(userMapper
                .getAllContactsForUser(uuid, contactService.getContactsByUserUid(uuid, pageable)
                        .getContent()));
    }

    private User getUserByUuid(UUID uuid) {
        return userRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist"));
    }

    public Page<ResponseUserDTO> getUsersByPage(Pageable pageable) {
        return new PageImpl<>(userMapper.getAllUsers(userRepository.findAllByOrderByLastNameAsc(pageable).getContent()));
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email : " + email + " does not exist"));
    }
}
