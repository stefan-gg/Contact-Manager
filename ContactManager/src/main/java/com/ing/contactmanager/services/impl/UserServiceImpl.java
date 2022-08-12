package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
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
public class UserServiceImpl implements CRUDService<ResponseUserDTO, RequestUserDTO> {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDTO getByUuid(UUID uuid) {
        return userMapper.convertToUserDTO(userRepository.findByUid(uuid).orElseThrow(
                () -> new EntityNotFoundException(
                        "Element with UUID : " + uuid.toString() + " does not exist")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseUserDTO createOrUpdate(RequestUserDTO requestUserDTO, UUID uuid) {

        if (uuid == null) {
            requestUserDTO.setUuid(UUID.randomUUID());

            User user = userMapper.convertPostUserDTOToUser(requestUserDTO);

            userRepository.save(user);

            return userMapper.convertToUserDTO(user);

        } else {

            User user = getUserByUuid(uuid);
            User updatedUser = userMapper.convertPostUserDTOToUser(requestUserDTO);
            updatedUser.setId(user.getId());
            userRepository.save(updatedUser);

            return userMapper.convertToUserDTO(updatedUser);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<ResponseContactDTO> getContactsForUser(UUID uuid, Pageable pageable) {
        return new PageImpl<>(userMapper.getAllContactsForUser(uuid,
                getContactsByUserUid(uuid, pageable).getContent()));
    }

    private User getUserByUuid(UUID uuid) {
        return userRepository.findByUid(uuid).orElseThrow(() -> new EntityNotFoundException(
                "Element with UUID : " + uuid.toString() + " does not exist"));
    }

    public Page<ResponseUserDTO> getUsers(Pageable pageable) {
        return new PageImpl<>(userMapper.getAllUsers(
                userRepository.findAllByOrderByLastNameAsc(pageable).getContent()));
    }

    public Page<Contact> getContactsByUserUid(UUID uuid, Pageable page) {
        return new PageImpl<>(contactRepository.getContactsByUser_Uid(uuid, page).orElseThrow(
                () -> new EntityNotFoundException(
                        "Element with UUID : " + uuid.toString() + " does not exist")).getContent());
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmail(userEmail).orElseThrow(
                () -> new NoSuchElementException(
                        "User with email : " + userEmail + " does not exist"));
    }
}
