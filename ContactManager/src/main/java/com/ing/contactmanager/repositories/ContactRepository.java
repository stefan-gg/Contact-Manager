package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}