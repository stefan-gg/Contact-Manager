package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.controllers.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements CRUDService<ResponseContactDTO, RequestContactDTO> {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ContactMapper contactMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        contactRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseContactDTO getByUuid(UUID uuid) {
        return contactMapper.convertContactToContactDTO(contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestContactDTO createOrUpdate(RequestContactDTO postRequestContactDTO, UUID uuid) {

        User user = userRepository.getUserByEmail(postRequestContactDTO.getUserEmail());

        ContactType contactType = contactTypeRepository
                .getContactTypeByContactTypeName(postRequestContactDTO
                        .getContactTypeName());

//        postRequestContactDTO.setPostUserDTO(postUserMapper.covertUserToPostUserDTO(user));
//        postRequestContactDTO.setPostContactTypeDTO(postContactTypeMapper
//                .convertContactTypeToPostContactTypeDTO(contactType));
//
//        System.out.println(postRequestContactDTO.getPostUserDTO().getId());
//        System.out.println(postRequestContactDTO.getPostContactTypeDTO().getId());

        if (uuid == null) {

            Contact contact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO);

            contact.setUid(UUID.randomUUID());
            contact.setContactType(contactType);
            contact.setUser(user);

            contactRepository.save(contact);
            postRequestContactDTO.setUuid(contact.getUid());

        } else {

            Contact contact = getContactByUuid(uuid);
            Contact updatedContact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO);
            updatedContact.setUser(user);
            updatedContact.setContactType(contactType);
            updatedContact.setId(contact.getId());

            contactRepository.save(updatedContact);

            postRequestContactDTO.setUuid(uuid);
        }

        return postRequestContactDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseContactDTO> getAll() {
        return contactMapper.getAllContacts(contactRepository.findAll());
    }

    private Contact getContactByUuid(UUID uuid) {
        return contactRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist"));
    }

    public List<Contact> getContactsByUserUid(UUID uuid){
        return contactRepository
                .getContactsByUser_Uid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist"));
    }

    public List<ResponseContactDTO> getContactsByPage(int pageNum, int numOfElements){
        Pageable page = PageRequest.of(pageNum, numOfElements);
        return contactMapper.getAllContacts(contactRepository.findAllByOrderByLastNameAsc(page));
    }
}
