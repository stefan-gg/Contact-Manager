package com.ing.contactmanager.services.maps;

import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public List<UserDTO> getAllUsers() {
        return ((List<User>) userRepository
                .findAllByOrderByLastNameAsc())
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    };

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        List<Contact> contacts = contactRepository.getContactsByUser_Uid(user.getUid());

//        userDTO.setId(user.getId());
//        userDTO.setUuid(user.getUid());
//        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName((user.getFirstName()));
        userDTO.setLastName(user.getLastName());
//        userDTO.setCreatedAt(user.getCreatedAt());
//        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setRole(user.getRole().toString());
        userDTO.setContacts(contactMapper.convertContactsToContactsDTO(user.getContacts()));

        return userDTO;
    };
}
