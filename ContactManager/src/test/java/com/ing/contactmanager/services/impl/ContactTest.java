package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import com.ing.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RequiredArgsConstructor
public class ContactTest {

    @InjectMocks
    @Autowired
    private ContactServiceImpl contactService;
    @MockBean(ContactRepository.class)
    private ContactRepository contactRepository;

    @BeforeEach
    void init(@Mock ContactRepository contactRepository){
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
                new Contact(1, UUID.randomUUID(), "Ime", "Prezime",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        Contact contact3 =
                new Contact(1, UUID.randomUUID(), "Im", "Pr",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        Page<Contact> pageContact = new PageImpl<>(contacts);

        Mockito.lenient().when(contactRepository.searchContactsByUser(Mockito.any(), Mockito.any(),
                        Mockito.any()))
                .thenReturn(pageContact);
    }

    @DataProvider(name = "search-data")
    public Object[][] getContacts(Method method) {
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
                new Contact(1, UUID.randomUUID(), "Ime", "Prezime",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        Contact contact3 =
                new Contact(1, UUID.randomUUID(), "Im", "Pr",
                        null, null, "0120123", "mail@gmail.com",
                        LocalDateTime.now(), LocalDateTime.now(), user1, c);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        switch (method.getName()) {
            case "getAllContactsBySearchQuery":
                return new Object[][]{{"me", contacts}};
        }
        return null;
    }

    @Test(dataProvider = "search-data")
    void getAllContactsBySearchQuery(String searchParam, ArrayList<Contact> ct) {


        Pageable pageable = PageRequest.of(0, 10);

        List<ResponseContactDTO> contacts = new ArrayList<>();

//        Mockito.when(contactRepository.saveAll(ct)).thenReturn(ct);
//
//        contactRepository.saveAll(ct);

        Page<Contact> pageContact = new PageImpl<>(ct);

        Mockito.when(contactRepository.searchContactsByUser(Mockito.any(), Mockito.any(),
                        Mockito.any()))
                .thenReturn(pageContact);

        Assertions.assertEquals(ct.size(), contactService.getContactsBySearchQuery(
                searchParam,
                "ema@das.com",
                pageable,
                true
        ).getSize());
    }

}