package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.mappers.ContactMapper;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.DataProvider;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RequiredArgsConstructor
public class ContactServiceImplTest {
    @MockBean(ContactRepository.class)
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    @InjectMocks
    private ContactServiceImpl contactService;

    @DataProvider(name = "getContacts")
    public Object[][] getContacts(String caseName) {
        User user1 = new User(1, UUID.randomUUID(), null, "user1@das.com",
                "1234", "Ime", "pREZ",
                Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now());

        User user2 = new User(2, UUID.randomUUID(), null, "user2@das.com",
                "1234", "Ime", "pREZ",
                Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now());

        User user3 = new User(3, UUID.randomUUID(), null, "user3@das.com",
                "1234", "Ime", "pREZ",
                Role.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());

        ContactType c = new ContactType();
        c.setContactTypeName("friend");

        Contact contact1 =
                new Contact(1, UUID.randomUUID(), "Ime", "Prezime",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        Contact contact2 =
                new Contact(2, UUID.randomUUID(), "Ime", "Prezime",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        Contact contact3 =
                new Contact(3, UUID.randomUUID(), "Im", "Pr",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);

        Pageable pageable = PageRequest.of(0, 10);

        switch (caseName) {
            case "user":

                List<ResponseContactDTO> contactsDTO =
                        contactMapper.convertContactsToContactsDTO(contacts);

                return new Object[][]{{"me", contacts, pageable, contactsDTO, false}};

            case "admin":
                contacts.add(contact3);

                contactsDTO =
                        contactMapper.convertContactsToContactsDTO(contacts);

                return new Object[][]{{"m", contacts, pageable, contactsDTO, true}};

            case "returnEmptyList":

                contactsDTO = Collections.emptyList();

                return new Object[][]{{"me", contacts, pageable, contactsDTO, false}};
        }
        return null;
    }

    @Test
    @UseDataProvider("getContacts")
    void getContactsBySearchQuery() {

        Object[][] testParams = getContacts("user");
        List<Contact> contacts = (List<Contact>) testParams[0][1];

        Pageable pageable = (Pageable) testParams[0][2];

        List<ResponseContactDTO> contactsDTO = (List<ResponseContactDTO>) testParams[0][3];

        Page<ResponseContactDTO> pageResponse = new PageImpl<>(contactsDTO);
        Page<Contact> pageContact = new PageImpl<>(contacts);

        Mockito.when(contactRepository.searchContactsByUser(Mockito.any(), Mockito.any(),
                        Mockito.any()))
                .thenReturn(Optional.of(pageContact));

        Assertions.assertEquals(pageResponse.getSize(), contactService.getContactsBySearchQuery(
                (String) testParams[0][0],
                contacts.get(0).getUser().getEmail(),
                pageable,
                (boolean) testParams[0][4]
        ).getSize());
    }

    @Test
    @UseDataProvider("getContacts")
    void getContactsBySearchQueryAsAdmin() {

        Object[][] testParams = getContacts("admin");
        List<Contact> contacts = (List<Contact>) testParams[0][1];


        Pageable pageable = (Pageable) testParams[0][2];

        List<ResponseContactDTO> contactsDTO = (List<ResponseContactDTO>) testParams[0][3];

        Page<ResponseContactDTO> pageResponse = new PageImpl<>(contactsDTO);

        Page<Contact> pageContact = new PageImpl<>(contacts);

        Mockito.when(contactRepository.searchAllContacts(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(pageContact));

        Assertions.assertEquals(pageResponse.getSize(), contactService.getContactsBySearchQuery(
                (String) testParams[0][0],
                null,
                pageable,
                (boolean) testParams[0][4]
        ).getSize());
    }

    @Test
    @UseDataProvider("getContacts")
    void getEmptyListBySearchQuery() {

        NoSuchElementException thrown =
                Assertions.assertThrows(NoSuchElementException.class, () -> {

                    Object[][] testParams = getContacts("returnEmptyList");
                    List<Contact> contacts = (List<Contact>) testParams[0][1];


                    Pageable pageable = (Pageable) testParams[0][2];

                    List<ResponseContactDTO> contactsDTO =
                            (List<ResponseContactDTO>) testParams[0][3];

                    Page<ResponseContactDTO> pageResponse = new PageImpl<>(contactsDTO);

                    Page<Contact> pageContact = new PageImpl<>(contacts);

                    Mockito.when(contactRepository.searchAllContacts(Mockito.any(), Mockito.any()))
                            .thenReturn(Optional.of(pageContact));

                    Assertions.assertEquals(pageResponse.getSize(),
                            contactService.getContactsBySearchQuery(
                                    (String) testParams[0][0],
                                    contacts.get(0).getUser().getEmail(),
                                    pageable,
                                    (boolean) testParams[0][4]
                            ).getSize());
                });

        Assertions.assertEquals("There are no results that matched your search param: me.",
                thrown.getMessage());
    }
}