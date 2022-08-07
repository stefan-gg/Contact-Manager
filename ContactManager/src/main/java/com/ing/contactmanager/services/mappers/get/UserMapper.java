package com.ing.contactmanager.services.mappers.get;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.controllers.dtos.get.user.UserDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
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
    }

    public List<ContactDTO> getAllContactsForUser(UUID uuid){
        List<Contact> contacts = contactRepository.getContactsByUser_Uid(uuid);
        List<ContactDTO> contactsDTO = contactMapper.convertContactsToContactsDTO(contacts);
        return contactsDTO;
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        List<Contact> contacts = contactRepository.getContactsByUser_Uid(user.getUid());

//        userDTO.setId(user.getId());
        userDTO.setUuid(user.getUid());
//        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName((user.getFirstName()));
        userDTO.setLastName(user.getLastName());
//        userDTO.setCreatedAt(user.getCreatedAt());
//        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setRole(user.getRole().toString());
        userDTO.setContacts(contactMapper.convertContactsToContactsDTO(contacts));

        return userDTO;
    }
}
