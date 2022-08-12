package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.ContactMapper;
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
public class ContactServiceImpl implements CRUDService<ResponseContactDTO, RequestContactDTO> {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final ContactTypeRepository contactTypeRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {

        User loggedUser = userService.getLoggedInUser();

        Contact contact = contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist"));

        if (loggedUser.getId() == contact.getUser().getId() || loggedUser.getRole().toString().equals(ROLE_ADMIN)) {
            contactRepository.deleteByUid(uuid);
        } else {
            throw new RuntimeException("Method denied");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseContactDTO getByUuid(UUID uuid) {

        User loggedUser = userService.getLoggedInUser();

        Contact contact = contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist"));

        if (loggedUser.getId().equals(contact.getUser().getId()) || loggedUser.getRole().toString().equals(ROLE_ADMIN)) {
            return contactMapper.convertContactToContactDTO(contact);
        } else {
            throw new RuntimeException("Method denied");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseContactDTO createOrUpdate(RequestContactDTO postRequestContactDTO, UUID uuid) {
        User loggedUser = userService.getLoggedInUser();

        User user = getUser(postRequestContactDTO);

        ContactType contactType = contactTypeRepository
                .getContactTypeByContactTypeName(postRequestContactDTO
                        .getContactTypeName())
                .orElseThrow(() -> new EntityNotFoundException("ContactType with passed UUID does not exist"));

        if (uuid == null) {

            Contact contact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO);

            contact.setUid(UUID.randomUUID());
            contact.setContactType(contactType);
            contact.setUser(user);

            if (contact.getUser().getId() == loggedUser.getId() && !loggedUser.getRole().toString().equals(ROLE_ADMIN)) {
                contactRepository.save(contact);
                return contactMapper.convertContactToContactDTO(contact);
            } else {
                throw new RuntimeException("Method denied");
            }

        } else {

            Contact contact = getContactByUuid(uuid);

            if (loggedUser.getId() == contact.getUser().getId() || loggedUser.getRole().toString().equals(ROLE_ADMIN)) {
                Contact updatedContact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO);
                updatedContact.setUser(user);
                updatedContact.setContactType(contactType);
                updatedContact.setId(contact.getId());

                contactRepository.save(updatedContact);

                return contactMapper.convertContactToContactDTO(updatedContact);
            } else {
                throw new RuntimeException("Method denied");
            }
        }
    }

    private User getUser(RequestContactDTO postRequestContactDTO) {
        return userRepository
                .getUserByEmail(postRequestContactDTO
                        .getUserEmail())
                .orElseThrow(() -> new NoSuchElementException("User with email : " + postRequestContactDTO
                        .getUserEmail() + "does not exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseContactDTO> getAll() {
        return new PageImpl<>(contactMapper.getAllContacts(contactRepository.findAll()));
    }

    private Contact getContactByUuid(UUID uuid) {
        return contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist"));
    }

    public boolean compareTwoUsers(User loggedUser, User user){
        if (loggedUser.getId() == user.getId() || loggedUser.getRole().toString().equals(ROLE_ADMIN)) {
            return true;
        } else {
            throw new RuntimeException("Method denied");
        }
    }

    public Page<ResponseContactDTO> getContactsByPage(Pageable page) {
        return new PageImpl<>(contactMapper
                .getAllContacts(contactRepository
                        .findAllByOrderByLastNameAsc(page).getContent()));
    }
}
