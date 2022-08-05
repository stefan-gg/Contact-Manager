package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.services.mappers.UserMapper;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CRUDService<UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO getByUuid(UUID uuid) {
        return userMapper.convertToUserDTO(userRepository.findByUid(uuid));//.orElseThrow(() -> new NoSuchElementException("UserService.notFound"));
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public UserDTO createOrUpdate(UserDTO userDTO) {
        //userDTO.setUuid(UUID.randomUUID());
        return new UserDTO();
        //return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserDTO> getAll() {
        return userMapper.getAllUsers();
    }
}
