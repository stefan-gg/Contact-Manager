package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findByUid(UUID uuid);

    @Transactional
    void deleteByUid(UUID uuid);
}