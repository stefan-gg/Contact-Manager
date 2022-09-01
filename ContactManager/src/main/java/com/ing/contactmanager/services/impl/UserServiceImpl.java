package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.mappers.UserMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final MailServiceImpl mailService;

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
    public ResponseUserDTO createOrUpdate(RequestUserDTO requestUserDTO, UUID uuid) {

        if (uuid == null) {

            User user = userMapper.convertPostUserDTOToUser(requestUserDTO);
            user.setUid(UUID.randomUUID());
            userRepository.save(user);

            try {
                mailService.sendConfirmationEmail(user.getEmail(),
                        user.getFirstName(), user.getLastName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            return userMapper.convertToUserDTO(user);

        } else {

            User user = getUserByUuid(uuid);
            User updatedUser = userMapper.convertPostUserDTOToUser(requestUserDTO);
            updatedUser.setId(user.getId());
            userRepository.save(updatedUser);

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

    private User getUserByUuid(UUID uuid) {
        return userRepository.findByUid(uuid).orElseThrow(() -> new EntityNotFoundException(
                "Element with UUID : " + uuid.toString() + " does not exist"));
    }
}
