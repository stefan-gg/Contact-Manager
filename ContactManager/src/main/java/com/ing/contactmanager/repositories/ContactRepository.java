package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByUid(UUID uuid);

    List<Contact> getContactsByUser_Uid(UUID uuid);

    void deleteByUid(UUID uuid);
}