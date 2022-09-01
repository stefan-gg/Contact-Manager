package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.controllers.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.VerificationStatus;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Transactional(readOnly = true)
    public ResponseUserDTO getByUuid(UUID uuid) {
        return userMapper.convertToUserDTO(userRepository.findByUid(uuid).orElseThrow(
                () -> new EntityNotFoundException(
                        "Element with UUID : " + uuid.toString() + " does not exist")));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseUserDTO userSelfUpdate(RequestUserDTO requestUserDTO, String email) {

        User user = getUserByEmail(email);
        User updatedUser = userMapper.convertPostUserDTOToUser(requestUserDTO);
        updatedUser.setId(user.getId());
        updatedUser.setEmail(email);
        userRepository.save(updatedUser);

        return userMapper.convertToUserDTO(updatedUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseUserDTO createOrUpdate(RequestUserDTO requestUserDTO, UUID uuid) {

        if (uuid == null) {

            User user = userMapper.convertPostUserDTOToUser(requestUserDTO);
            user.setUid(UUID.randomUUID());
            user.setVerificationStatus(VerificationStatus.UNVERIFIED);
            userRepository.save(user);

            return userMapper.convertToUserDTO(user);

        } else {

            User user = getUserByUuid(uuid);
            User updatedUser = userMapper.convertPostUserDTOToUser(requestUserDTO);
            updatedUser.setId(user.getId());
            updateUser(updatedUser);

            return userMapper.convertToUserDTO(updatedUser);
        }
    }

    @Transactional(readOnly = true)
    public Page<ResponseUserDTO> getUsers(Pageable pageable) {
        return new PageImpl<>(userMapper.getAllUsers(
                userRepository.findAllByOrderByLastNameAsc(pageable).getContent()));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmail(userEmail).orElseThrow(
                () -> new NoSuchElementException(
                        "User with email : " + userEmail + " does not exist"));
    }

    public void updateUser(User updatedUser) {
        userRepository.save(updatedUser);
    }

    private User getUserByUuid(UUID uuid) {
        return userRepository.findByUid(uuid).orElseThrow(() -> new EntityNotFoundException(
                "Element with UUID : " + uuid.toString() + " does not exist"));
    }
}