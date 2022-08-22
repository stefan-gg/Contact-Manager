package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.mappers.ContactMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserServiceImpl userService;
    private final ContactRepository contactRepository;
    private final ContactTypeServiceImpl contactTypeService;
    private final ContactMapper contactMapper;

    private final Validator validator;


    @Transactional(readOnly = true)
    public Page<ResponseContactDTO> getContacts(Pageable page, User user) {
        return new PageImpl<>(contactMapper
                .convertContactsToContactsDTO(contactRepository
                        .findContactsByUser_Uid(user.getUid(), page).getContent()));
    }

    @Transactional(readOnly = true)
    public Page<Contact> getContactsByUserUid(UUID uuid, Pageable page) {
        return new PageImpl<>(contactRepository.getContactsByUser_Uid(uuid, page).orElseThrow(
                        () -> new EntityNotFoundException(
                                "Element with UUID : " + uuid.toString() + " does not exist"))
                .getContent());
    }

    public ResponseEntity importContactsFromFile(MultipartFile file, String userEmail) {
        int contactsImportedSuccessfully = 0;
        int contactsFailedToImport = 0;

        Map<String, String> map = new HashMap<>();

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("File is empty");
        } else {

            List<Set<ConstraintViolation<Object>>> errors = new ArrayList<>();

            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<RequestContactDTO> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(RequestContactDTO.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withIgnoreEmptyLine(true)
                        .build();

                List<RequestContactDTO> contacts = csvToBean.parse();

                for (RequestContactDTO contactDTO : contacts) {
                    if (validator.validate(contactDTO).size() != 0) {
                        contactsFailedToImport++;

                        errors.add(validator.validate(contactDTO));
                    } else {
                        createOrUpdate(contactDTO, null, userEmail);
                        contactsImportedSuccessfully++;
                    }
                }

                if (!errors.isEmpty()) {

                    map.put("Message", contactsFailedToImport + " records were incomplete, " +
                            "contacts imported: " + contactsImportedSuccessfully);
                    map.put("Errors", errors.toString());

                    return contactsImportedSuccessfully > 0 ? ResponseEntity.status(HttpStatus.OK)
                            .body(map) : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(map);
                } else if (contacts.isEmpty()) {
                    map.put("Message", "No contacts provided in the file.");

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(map);
                } else {
                    return ResponseEntity.ok().build();
                }
            } catch (Exception ex) {

                map.put("Message",
                        "File wasn't read. Either the format was incorrect or the file was " +
                                "corrupt.");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(map);
            }
        }
    }

    @Transactional(readOnly = true)
    public ResponseContactDTO getByUuid(UUID uuid) {

        Contact contact = contactRepository.findByUid(uuid).orElseThrow(
                () -> new NoSuchElementException(
                        "Element with UUID : " + uuid.toString() + " does not exist"));

        return contactMapper.convertContactToContactDTO(contact);
    }

    @Transactional(readOnly = true)
    public Page<ResponseContactDTO> getContactsBySearchQuery(String searchParam, String userEmail,
                                                             Pageable pageable, boolean isAdmin) {
        if (!isAdmin) {
            return new PageImpl<>(contactMapper.convertContactsToContactsDTO(
                    contactRepository
                            .searchContactsByUser(
                                    searchParam,
                                    userEmail,
                                    pageable)
                            .getContent()));

        } else {
            return new PageImpl<>(contactMapper.convertContactsToContactsDTO(
                    contactRepository.searchAllContacts(searchParam,
                                    pageable)
                            .getContent()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseContactDTO createOrUpdate(RequestContactDTO postRequestContactDTO, UUID uuid,
                                             String userEmail)
            throws AccessDeniedException {

        ContactType contactType = contactTypeService.getContactType(postRequestContactDTO);
        Contact contact = new Contact();

        if (uuid == null) {

            User user = userService.getUserByEmail(userEmail);

            contact = contactMapper.convertPostContactDTOToContact(postRequestContactDTO, contact);

            contact.setUid(UUID.randomUUID());
            contact.setContactType(contactType);
            contact.setUser(user);

            contactRepository.save(contact);
            return contactMapper.convertContactToContactDTO(contact);

        } else {

            contact = getContactByUuid(uuid);

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
