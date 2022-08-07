package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.controllers.dtos.post.contact.PostContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.get.ContactMapper;
import com.ing.contactmanager.services.mappers.post.PostContactMapper;
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
public class ContactServiceImpl implements CRUDService<ContactDTO, PostContactDTO> {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ContactMapper contactMapper;
    private final PostContactMapper postContactMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void deleteByUuid(UUID uuid) {
        contactRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ContactDTO getByUuid(UUID uuid) {
        return contactMapper.convertContactToContactDTO(contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist")));
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public PostContactDTO createOrUpdate(PostContactDTO postContactDTO, UUID uuid) {

        User user = userRepository.getUserByEmail(postContactDTO.getUserEmail());

        ContactType contactType = contactTypeRepository
                .getContactTypeByContactTypeName(postContactDTO
                        .getContactTypeName());

//        postContactDTO.setPostUserDTO(postUserMapper.covertUserToPostUserDTO(user));
//        postContactDTO.setPostContactTypeDTO(postContactTypeMapper
//                .convertContactTypeToPostContactTypeDTO(contactType));
//
//        System.out.println(postContactDTO.getPostUserDTO().getId());
//        System.out.println(postContactDTO.getPostContactTypeDTO().getId());

        if (uuid == null) {

            Contact contact = postContactMapper.convertPostContactDTOToContact(postContactDTO);

            contact.setUid(UUID.randomUUID());
            contact.setContactType(contactType);
            contact.setUser(user);

            contactRepository.save(contact);
            postContactDTO.setUuid(contact.getUid());

        } else {

            Contact contact = getContactByUuid(uuid);
            Contact updatedContact = postContactMapper.convertPostContactDTOToContact(postContactDTO);
            updatedContact.setUser(user);
            updatedContact.setContactType(contactType);
            updatedContact.setId(contact.getId());

            contactRepository.save(updatedContact);

            postContactDTO.setUuid(uuid);
        }

        return postContactDTO;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public List<ContactDTO> getAll() {
        return contactMapper.getAllContacts();
    }

    private Contact getContactByUuid(UUID uuid) {
        return contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist"));
    }
}
