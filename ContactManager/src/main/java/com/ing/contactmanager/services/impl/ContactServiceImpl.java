package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.mappers.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserServiceImpl userService;
    private final ContactRepository contactRepository;
    private final ContactTypeServiceImpl contactTypeService;
    private final ContactMapper contactMapper;


    @Transactional(readOnly = true)
    public Page<ResponseContactDTO> getContacts(Pageable page, User user) {
        return new PageImpl<>(contactMapper
                .getAllContacts(contactRepository
                        .findContactsByUser_Uid(user.getUid(), page).getContent()));
    }

    @Transactional(readOnly = true)
    public Page<Contact> getContactsByUserUid(UUID uuid, Pageable page) {
        return new PageImpl<>(contactRepository.getContactsByUser_Uid(uuid, page).orElseThrow(
                        () -> new EntityNotFoundException(
                                "Element with UUID : " + uuid.toString() + " does not exist"))
                .getContent());
    }

    @Transactional(readOnly = true)
    public ResponseContactDTO getByUuid(UUID uuid) {


        Contact contact = contactRepository.findByUid(uuid).orElseThrow(
                () -> new NoSuchElementException(
                        "Element with UUID : " + uuid.toString() + " does not exist"));

        return contactMapper.convertContactToContactDTO(contact);
    }

//    @Transactional(readOnly = true)
//    public Page<ResponseContactDTO> getContactsBySearchQuery(String searchParam, String userEmail,
//                                                             Pageable pageable) {
//
//
//        return new PageImpl<>(contactMapper.convertContactsToContactsDTO(
//                contactRepository
//                        .getContactsByAddressContainsAndEmailContainsAndFirstNameContainsAndLastNameContainsAndUser_Email(
//                                searchParam, searchParam, searchParam, searchParam,
//                                userEmail,
//                                pageable)
//                        .orElseThrow(
//                                () -> new NoSuchElementException(
//                                        "There are no elements that match your search"))
//                        .getContent()));
//    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseContactDTO createOrUpdate(RequestContactDTO postRequestContactDTO, UUID uuid,
                                             String userEmail)
            throws AccessDeniedException {


        ContactType contactType = contactTypeService.getContactType(postRequestContactDTO);

        if (uuid == null) {

            User user = userService.getUserByEmail(userEmail);

            Contact contact = new Contact();
            contact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO, contact);

            contact.setUid(UUID.randomUUID());
            contact.setContactType(contactType);
            contact.setUser(user);

            contactRepository.save(contact);
            return contactMapper.convertContactToContactDTO(contact);

        } else {

            Contact contact = getContactByUuid(uuid);

            if (contact.getUser().getEmail().equals(userEmail)) {

                contact =
                        contactMapper.convertPostContactDTOToContact(postRequestContactDTO,
                                contact);

                contact.setContactType(contactType);

                contactRepository.save(contact);

                return contactMapper.convertContactToContactDTO(contact);
            }

            throw new AccessDeniedException("Access denied");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid, String userEmail) throws AccessDeniedException {
        User user = userService.getUserByEmail(userEmail);

        Contact contact = getContactByUuid(uuid);

        if (contact.getUser().getId().equals(user.getId()) || user.getRole().toString().equals(
                ROLE_ADMIN)) {

            contactRepository.deleteByUid(uuid);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<ResponseContactDTO> getContactsForUser(UUID uuid, Pageable pageable) {
        return new PageImpl<>(contactMapper.convertContactsToContactsDTO(
                getContactsByUserUid(uuid, pageable).getContent()));
    }

    private Contact getContactByUuid(UUID uuid) {
        return contactRepository.findByUid(uuid).orElseThrow(() -> new NoSuchElementException(
                "Element with UUID : " + uuid.toString() + " does not exist"));
    }
}
