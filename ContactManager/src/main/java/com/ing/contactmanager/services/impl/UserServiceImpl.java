package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.controllers.dtos.post.user.PostUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.services.mappers.get.UserMapper;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.post.PostUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CRUDService<UserDTO, PostUserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PostUserMapper postUserMapper;

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO getByUuid(UUID uuid) {
        return userMapper.convertToUserDTO(userRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist")));
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public PostUserDTO createOrUpdate(PostUserDTO postUserDTO, UUID uuid) {
        if (uuid == null) {
            postUserDTO.setUuid(UUID.randomUUID());
            userRepository.save(postUserMapper.convertPostUserDTOToUser(postUserDTO));

        } else {
            User user = getUserByUuid(uuid);
            User updatedUser = postUserMapper.convertPostUserDTOToUser(postUserDTO);

            updatedUser.setId(user.getId());
            updatedUser.setUid(user.getUid());

            userRepository.save(updatedUser);

        }

        return postUserDTO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserDTO> getAll() {
        return userMapper.getAllUsers();
    }

    public List<ContactDTO> getContactsForUser(UUID uuid) {
        return userMapper.getAllContactsForUser(uuid);
    }

    private User getUserByUuid(UUID uuid) {
        return userRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist"));
    }
}
