package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.mappers.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnit4ClassRunner.class)
//@ExtendWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@RequiredArgsConstructor
class ContactServiceImplTest {
    @MockBean(ContactRepository.class)
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

//    @Mock
//    private Page<Contact> page = new PageImpl<>(Collections.emptyList());

    @Autowired
    @InjectMocks
    private ContactServiceImpl contactService;

    @DataProvider(name = "search-data")
    public Object[][] getContacts() {


        return new Object[][]{};
    }

    @Test
    void getContactsBySearchQuery() {
        Pageable pageable = PageRequest.of(0, 10);
//        Mockito.when(contactRepository.searchContactsByUser(Mockito.any(),
//                Mockito.any(), Mockito.any())).thenReturn(page);

        List<ResponseContactDTO> contacts = new java.util.ArrayList<>();
        List<Contact> ct = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setEmail("ema@das.com");
        user.setRole(Role.ROLE_USER);
        user.setPassword("1234");
        user.setFirstName("Ime");
        user.setLastName("1234");

        ContactType c = new ContactType();
        c.setContactTypeName("friend");

        Contact contact = new Contact();

        contact.setUser(user);
        contact.setContactType(c);
        contact.setFirstName("Ime");
        contact.setLastName("Ime");
        contact.setEmail("mail@gmail.com");
        contact.setPhoneNumber("1233123");

        ct.add(contact);

        contactRepository.save(contact);

        contacts.add(contactMapper.convertContactToContactDTO(contact));

        Page<ResponseContactDTO> pageResponse = new PageImpl<>(contacts);
        Page<Contact> pageContact = new PageImpl<>(ct);

        Mockito.when(contactRepository.searchContactsByUser(Mockito.any(), Mockito.any(),
                        Mockito.any()))
                .thenReturn(pageContact);

        Assertions.assertEquals(pageResponse.getSize(), contactService.getContactsBySearchQuery(
                "Ime",
                "ema@das.com",
                pageable,
                false
        ).getSize());
    }

//    @Test
//    void createOrUpdate() {
//    }
//
//    @Test
//    void getContactsForUser() {
//    }
//
//    @Test
//    void importContactsFromFile() {
//    }
}